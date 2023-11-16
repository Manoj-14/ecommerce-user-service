package com.project.ecommerceuser.cart.cartService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "cart-service",url = "${CART_SERVICE_URI:http://localhost}:8083")
public interface CartProxy {

    @PostMapping("/api/cart/{user_id}/addtocart")
    public void addToCart(@PathVariable String user_id, @RequestBody Map<String,String> request_body);
}
