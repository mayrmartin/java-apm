package com.example.restapi.dto;

import com.example.restapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreatePurchaseOrderDTO {
    private List<String> products;
}
