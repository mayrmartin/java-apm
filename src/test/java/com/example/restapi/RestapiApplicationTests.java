package com.example.restapi;

import com.example.restapi.dto.CreateProductDTO;
import com.example.restapi.dto.CreateProductResponseDTO;
import com.example.restapi.dto.CreatePurchaseOrderDTO;
import com.example.restapi.dto.CreatePurchaseOrderRespondDTO;
import com.example.restapi.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestapiApplicationTests {


    private Logger LOG = LoggerFactory.getLogger(RestapiApplication.class);

    private final String productApi = "http://localhost:8080/api/products";
    private final String orderApi = "http://localhost:8080/api/orders";
    private List<String> products = new ArrayList<>();
    private String order = "";


    @Test
    public void init() throws Exception {
        createProducts();
        createOrder();
    }

    @Test
    public void createProducts() throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i = 0; i< 5; i++){
            CreateProductDTO testProduct = new CreateProductDTO();
            testProduct.setName("Product "+i);
            testProduct.setDescription("This is a tester product");
            testProduct.setType("CUSTOM");
            testProduct.setCategory("SPECIAL");

            String content = objectMapper.writeValueAsString(testProduct);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(content,headers);

            CreateProductResponseDTO responseDTO = restTemplate.postForObject(productApi+"/",entity, CreateProductResponseDTO.class);

            LOG.info("Created Product: "+responseDTO.getId());
            products.add(responseDTO.getId());
        }

    }

    @Test
    public void createOrder() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        CreatePurchaseOrderDTO createPurchaseOrderDTO = new CreatePurchaseOrderDTO(products);

        String content = objectMapper.writeValueAsString(createPurchaseOrderDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(content,headers);

        CreatePurchaseOrderRespondDTO responseDTO = restTemplate.postForObject(orderApi+"/",entity, CreatePurchaseOrderRespondDTO.class);
        order = responseDTO.getId();
        LOG.info("Created Order: "+order);
    }

}
