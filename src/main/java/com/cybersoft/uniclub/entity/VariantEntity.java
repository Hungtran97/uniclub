package com.cybersoft.uniclub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "variant")
@Data
public class VariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sku;

    @Column(name = "images")
    private String image;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private  ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private  SizeEntity size;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

}

