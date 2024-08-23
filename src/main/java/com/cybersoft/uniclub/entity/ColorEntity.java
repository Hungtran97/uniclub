package com.cybersoft.uniclub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "color")
@Data
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "color")
    private List<ProductEntity> productList;
}
