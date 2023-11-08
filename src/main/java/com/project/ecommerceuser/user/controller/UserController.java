package com.project.ecommerceuser.user.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.ecommerceuser.product.productService.ProductProxy;
import com.project.ecommerceuser.user.entity.Address;
import com.project.ecommerceuser.user.entity.User;
import com.project.ecommerceuser.user.exception.UserNotFoundException;
import com.project.ecommerceuser.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductProxy productProxy;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<EntityModel> getUser(@PathVariable String id){
//        User user = userService.findUser(id);
//        EntityModel entityModel = EntityModel.of(user);
//        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
//        entityModel.add(link.withRel("all-users"));
//        return new ResponseEntity<>(entityModel, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<MappingJacksonValue> getUser(@PathVariable String id){
        User user = userService.findUser(id);
        MappingJacksonValue userValue = new MappingJacksonValue(user);
        SimpleBeanPropertyFilter userFilter  = SimpleBeanPropertyFilter.filterOutAllExcept("name","email","addresses");
        FilterProvider userFilterProvider = new SimpleFilterProvider().addFilter("userFilterBean",userFilter);
        userValue.setFilters(userFilterProvider);
        return new ResponseEntity<>(userValue, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user){
        try{
            User savedUser = userService.createUser(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
            return ResponseEntity.created(location).build();
        }catch(DuplicateKeyException une) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"User already exists");
        }
    }

    @PutMapping("/{email}/addAddress")
    public ResponseEntity<?> AddAddress(@PathVariable String email, @RequestBody Address address){
        try{
            User user = userService.addAddress(address,email);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (UserNotFoundException une){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody Map<String,String> request){
        try {
            userService.addToCart(request.get("productId"),request.get("userId"),Double.parseDouble(request.get("price")));
            return new ResponseEntity<>("Success",HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getLocalizedMessage());
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id){
        try{
            Map<String,String> product = productProxy.getProduct(id);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getLocalizedMessage());
        }
    }

}
