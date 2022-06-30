package com.netmeds.com.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


import java.util.Date;
import java.util.List;


@Data
public class Productdto {








    private String name;
    private long price;
    private String application;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expiry;

    private List<Integer> categoryIds;






}
