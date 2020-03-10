package com.example.restapi.service;

import com.example.restapi.dto.OrderInfromationDTO;
import com.example.restapi.model.PurchaseOrder;
import com.example.restapi.model.Product;
import com.example.restapi.repository.PurchaseOrderRepository;
import com.example.restapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private ProductRepository productRepository;

    public OrderInfromationDTO getOrderInformation(String orderId)
    {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.getOne(orderId);
        return new OrderInfromationDTO(purchaseOrder.getId(), purchaseOrder.getProducts());
    }
    public boolean updateOrder(String orderId,List<Product>products){
        PurchaseOrder o = purchaseOrderRepository.getOne(orderId);
        if(o != null) {
            o.setProducts(products);
            purchaseOrderRepository.save(o);
            return true;
        }
        else {
            return false;
        }
    }

    public String createOrder(List<String> products)
    {
        ArrayList<Product> p = new ArrayList<>();
        for(String t : products)
        {
            Product toInsert = productRepository.getOne(t);
            if(toInsert != null)
                p.add(toInsert);
        }

        PurchaseOrder o = new PurchaseOrder();
        o.setProducts(p);
        return purchaseOrderRepository.save(o).getId();
    }

    public boolean deleteOrder(String orderId){
        PurchaseOrder o = purchaseOrderRepository.getOne(orderId);
        if (o != null){
            purchaseOrderRepository.delete(o);
            return true;
        }
        else {
            return false;
        }
    }
}

