package com.netmeds.com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Products_category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productsCategoryId;

    private int productsId;

    private int categoryId;






}
