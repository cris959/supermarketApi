package com.cris959.SupermarketApi.service;


import com.cris959.SupermarketApi.dto.OrderItemDTO;
import com.cris959.SupermarketApi.dto.SaleDTO;
import com.cris959.SupermarketApi.exception.NotFoundException;
import com.cris959.SupermarketApi.mapper.Mapper;
import com.cris959.SupermarketApi.model.Branch;
import com.cris959.SupermarketApi.model.OrderItem;
import com.cris959.SupermarketApi.model.Product;
import com.cris959.SupermarketApi.model.Sale;
import com.cris959.SupermarketApi.repository.BranchRepository;
import com.cris959.SupermarketApi.repository.ProductRepository;
import com.cris959.SupermarketApi.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService {

    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    private final BranchRepository branchRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, BranchRepository branchRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
    }


    @Override
    public List<SaleDTO> getSales() {
        return saleRepository.findAllWithDetails()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public SaleDTO getSaleById(Long id) {
        // Buscamos la venta y si no existe lanzamos excepción
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found with ID: " + id));
        return Mapper.toDTO(sale);
    }

    @Override
    @Transactional
    public SaleDTO createSale(SaleDTO saleDTO) {
        // 1. Buscar Sucursal
        Branch branch = branchRepository.findById(saleDTO.branchId())
                .orElseThrow(() -> new NotFoundException("Branch not found!"));
        // 2. Crear la entidad Sale base
        Sale sale = new Sale();
        sale.setBranch(branch);
        sale.setDate(LocalDate.now());
        sale.setActive(true);

        double totalSale = 0.0;
        List<OrderItem> items = new ArrayList<>();

        // 3. Procesar cada producto del DTO
        for (OrderItemDTO orderItem : saleDTO.items()) {
            // Buscar el producto en la DB para tener el precio real y stock actual
            Product product = productRepository.findById(orderItem.id())
                    .orElseThrow(() -> new NotFoundException("Product ID " + orderItem.id() + " not found!"));

            // VALIDACIÓN: ¿Hay stock suficiente?
            if (product.getStock() < orderItem.quantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }

            // DESCONTAR STOCK
            product.setStock(product.getStock() - orderItem.quantity());
            productRepository.save(product); // Actualizamos el producto con el nuevo stock

            // Crear el item de venta (la relación)
            OrderItem saleItem = new OrderItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(orderItem.quantity());
            saleItem.setUnitPrice(product.getPrice()); // Guardamos el precio del momento
            saleItem.setSale(sale);

            totalSale += (product.getPrice() * orderItem.quantity());
            items.add(saleItem);
        }

        // 4. Finalizar la venta
        sale.setItems(items);
        sale.setTotal(totalSale);

        Sale savedSale = saleRepository.save(sale);
        return Mapper.toDTO(savedSale);
    }



//    @Override
//    @Transactional
//    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
//        // 1. Buscar la venta existente
//        Sale existingSale = saleRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Cannot update: Sale not found"));
//
//        // 2. REVERTIR STOCK: Devolvemos al inventario lo que se vendió originalmente
//        for (OrderItem item : existingSale.getItems()) {
//            Product product = item.getProduct();
//            product.setStock(product.getStock() + item.getQuantity());
//            productRepository.save(product);
//        }
//
//        // 3. ACTUALIZAR SUCURSAL (si cambió)
//        Branch branch = branchRepository.findById(saleDTO.branchId())
//                .orElseThrow(() -> new NotFoundException("Branch not found"));
//        existingSale.setBranch(branch);
//
//        // 4. LIMPIAR ÍTEMS ANTIGUOS Y PROCESAR NUEVOS
//        // Primero vaciamos la lista actual para que JPA pueda manejar la orfandad de los registros
//        existingSale.getItems().clear();
//
//        List<OrderItem> newItems = saleDTO.items().stream().map(itemDTO -> {
//            Product product = productRepository.findById(itemDTO.id())
//                    .orElseThrow(() -> new NotFoundException("Product ID " + itemDTO.id() + " not found!"));
//
//            // Validar stock nuevo (ahora que ya devolvimos el stock anterior)
//            if (product.getStock() < itemDTO.quantity()) {
//                throw new RuntimeException("Insufficient stock: " + product.getName());
//            }
//
//            // Restar stock nuevo
//            product.setStock(product.getStock() - itemDTO.quantity());
//            productRepository.save(product);
//
//            OrderItem newItem = new OrderItem();
//            newItem.setProduct(product);
//            newItem.setQuantity(itemDTO.quantity());
//            newItem.setUnitPrice(product.getPrice());
//            newItem.setSale(existingSale);
//            return newItem;
//        }).toList();
//
//        // 5. ASIGNAR NUEVOS VALORES Y RECALCULAR TOTAL
//        existingSale.getItems().addAll(newItems);
//
//        double newTotal = newItems.stream()
//                .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
//                .sum();
//        existingSale.setTotal(newTotal);
//        existingSale.setStatus("UPDATED");
//
//        // 6. Guardar y retornar
//        Sale updatedSale = saleRepository.save(existingSale);
//        return Mapper.toDTO(updatedSale);
//    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
// 1. Buscamos la venta (findById no la encontrará si ya está inactiva)
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The sale does not exist or has already been canceled"));

        // 2. Devolvemos el stock a cada producto
        for (OrderItem item : sale.getItems()) {
            Product product = item.getProduct();

            // Sumamos la cantidad que se había vendido originalmente
            product.setStock(product.getStock() + item.getQuantity());

            productRepository.save(product);
        }

        // 3. Cambiamos el estado (opcional, para que visualmente se vea "ANULADA")
        sale.setStatus("CANCELLED");
        saleRepository.save(sale);

        // 4. Ejecutamos el borrado lógico (hace el UPDATE active = false)
        saleRepository.delete(sale);
    }

    public List<SaleDTO> getArchivedSales() {
        return saleRepository.findInactiveSales()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public Double getTotalSalesByBranch(Long branchId) {
        // Verificamos si la sucursal existe primero
        if (!branchRepository.existsById(branchId)) {
            throw new NotFoundException("Branch not found");
        }

        Double total = saleRepository.sumTotalByBranch(branchId);
        return (total != null) ? total : 0.0; // Evitamos el NullPointerException
    }
}
