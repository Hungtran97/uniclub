package com.cybersoft.uniclub.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@Entity(name = "brand")
@Data
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<ProductEntity> productList;
}
