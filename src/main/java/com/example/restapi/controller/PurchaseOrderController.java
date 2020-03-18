package com.example.restapi.controller;

import com.example.restapi.dto.CreatePurchaseOrderDTO;
import com.example.restapi.dto.CreatePurchaseOrderRespondDTO;
import com.example.restapi.dto.GetProductsForOrderMessageResponseDTO;
import com.example.restapi.dto.UpdateOrderDTO;
import com.example.restapi.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders/")

public class PurchaseOrderController {

    private Logger LOG = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity getProduct(@PathVariable(name = "id") String id) {
        GetProductsForOrderMessageResponseDTO respondDTO = new GetProductsForOrderMessageResponseDTO(purchaseOrderService.getOrderInformation(id).getProducts());
        return ResponseEntity.ok().body(respondDTO);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOrder(@RequestBody CreatePurchaseOrderDTO orderDTO) {
        CreatePurchaseOrderRespondDTO respondDTO = new CreatePurchaseOrderRespondDTO(purchaseOrderService.createOrder(orderDTO.getProducts()));
        return  ResponseEntity.ok(respondDTO);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOrder(@RequestBody UpdateOrderDTO updateOrderDTO, @PathVariable(name = "id") String id) {
        boolean status = purchaseOrderService.updateOrder(id,updateOrderDTO.getProducts());
        if (status)
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.notFound().build();
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrder(@PathVariable(name = "id") String id) {
        boolean status = purchaseOrderService.deleteOrder(id);
        if (status)
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.notFound().build();
    }
}
