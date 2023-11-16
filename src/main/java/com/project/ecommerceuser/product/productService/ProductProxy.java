package com.project.ecommerceuser.product.productService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

//@FeignClient(name = "product-service",url = "localhost:8082")
@FeignClient(name = "product-service", url = "${PRODUCT_SERVICE_URI:http://localhost}:8082")
public interface ProductProxy {
    @GetMapping("/api/products/{id}/exists")
    public boolean existsByID(@PathVariable String id);

    @GetMapping("/api/products/{id}")
//    @Retry(name = "default",fallbackMethod = "handleFallBack")
    @CircuitBreaker(name = "default")
    public Map<String,String> getProduct(@PathVariable String id);

    default String handleFallBack(Exception ex){
        System.out.println("Fall back");
        return "Fall back";
    }
}
