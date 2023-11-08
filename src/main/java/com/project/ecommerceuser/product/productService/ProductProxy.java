package com.project.ecommerceuser.product.productService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

//@FeignClient(name = "product-service",url = "localhost:8082")
@FeignClient(name = "product-service")
public interface ProductProxy {
    @GetMapping("/api/products/{id}/exists")
    public boolean existsByID(@PathVariable String id);

    @GetMapping("/api/products/{id}")
    public Map<String,String> getProduct(@PathVariable String id);
}
