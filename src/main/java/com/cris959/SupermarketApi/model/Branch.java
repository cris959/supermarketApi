package com.cris959.SupermarketApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "branches")
@SQLDelete(sql = "UPDATE branches SET active = false WHERE id = ?")
@SQLRestriction("active = true")
@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @Column(nullable = false)
    private boolean active = true;
}
