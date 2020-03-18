package com.example.restapi.dto;

import com.example.restapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data

public class GetProductInformationResponseDTO {

    private String id;
    private String name;
    private String description;
    private String type;
    private String category;

    public GetProductInformationResponseDTO(Product p){
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.category = p.getCategory();
    }
}
