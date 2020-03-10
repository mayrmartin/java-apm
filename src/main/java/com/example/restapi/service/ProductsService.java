package com.example.restapi.service;

import com.example.restapi.dto.CreateProductDTO;
import com.example.restapi.model.Product;
import com.example.restapi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class ProductsService {

    private Logger LOG = LoggerFactory.getLogger(ProductsService.class);
    private ProductRepository productRepository;


    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(String id) {
        LOG.info("Getting the product with given id:" + id);
        Product p = productRepository.getOne(id);
        if (p != null)
            return productRepository.getOne(id);
        return null;
    }

    public String saveProduct(CreateProductDTO product) {
        Product p = new Product();
        try {
            TimeUnit.SECONDS.sleep(randomSleep(2,7));
            LOG.info("Saving product...");
            p.setCategory(product.getCategory());
            p.setDescription(product.getDescription());
            p.setName(product.getName());
            p.setType(product.getType());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return productRepository.save(p).getId();
    }


    public boolean updateProduct(Product productToUpdate, String id) {
        Product foundProduct = productRepository.getOne(id);
        if(foundProduct != null)
        {
            foundProduct.setName(productToUpdate.getName());
            foundProduct.setDescription(productToUpdate.getDescription());
            foundProduct.setType(productToUpdate.getType());
            foundProduct.setCategory(productToUpdate.getCategory());
            productRepository.save(foundProduct);
            return true;
        }
        else
            return false;
    }

    public boolean deleteProduct(String id) {
            Product p = productRepository.getOne(id);
            if (p != null){
                productRepository.delete(p);
                return true;
            }
            else{
                return false;
            }
    }
    private int randomSleep(int min,int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
