package com.example.restapi.controller;

import com.example.restapi.dto.CreateProductDTO;
import com.example.restapi.dto.CreateProductResponseDTO;
import com.example.restapi.dto.GetProductInformationResponseDTO;
import com.example.restapi.model.Product;
import com.example.restapi.service.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/products/")

public class ProductsController {

    private Logger LOG = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductsService productsService;

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity getProduct(@PathVariable(name = "id") String id) {

         Product p = productsService.getProduct(id);
         if (p != null)
             return ResponseEntity.ok(new GetProductInformationResponseDTO(p));
         else
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProduct(@RequestBody CreateProductDTO productToSave) {
        CreateProductResponseDTO resp = new CreateProductResponseDTO(productsService.saveProduct(productToSave));
        LOG.info("Created Product: "+resp.getId());
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProduct(@RequestBody Product productToUpdate, @PathVariable(name = "id") String id) {
        boolean p = productsService.updateProduct(productToUpdate, id);
        if (p)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProduct(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(productsService.deleteProduct(id));
    }
}
