package com.example.restapi.dto;

import com.example.restapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UpdateOrderDTO {
    private List<Product> products;
}
