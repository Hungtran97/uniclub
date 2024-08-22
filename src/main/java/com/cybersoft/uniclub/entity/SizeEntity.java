package com.cybersoft.uniclub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "size")
@Data
public class SizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "size")
    private List<ProductEntity> productList;
}
