package com.project.ecommerceuser.product.productService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",url = "localhost:8082")
public interface ProductProxy {
    @GetMapping("/api/products/{id}/exists")
    public boolean existsByID(@PathVariable String id);
}
