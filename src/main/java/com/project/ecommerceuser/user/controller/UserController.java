package com.project.ecommerceuser.user.controller;

import com.project.ecommerceuser.user.entity.Address;
import com.project.ecommerceuser.user.entity.User;
import com.project.ecommerceuser.user.exception.UserNotFoundException;
import com.project.ecommerceuser.user.repository.UserRepository;
import com.project.ecommerceuser.user.service.UserService;
import com.project.ecommerceuser.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<?> getAllUsers(){
//        List<User> users = userService.findAll();
//        return new ResponseEntity<>(users,HttpStatus.OK);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<EntityModel> getUser(@PathVariable String id){
//        User user = userService.findUser(id);
//        EntityModel entityModel = EntityModel.of(user);
//        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
//        entityModel.add(link.withRel("all-users"));
//        return new ResponseEntity<>(entityModel, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        User user = userService.findUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
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


}
