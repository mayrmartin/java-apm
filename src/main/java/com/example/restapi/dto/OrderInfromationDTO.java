package com.example.restapi.dto;

import com.example.restapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderInfromationDTO {
        private String orderId;
        private List<Product> products;

}
