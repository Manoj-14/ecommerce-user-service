package com.project.ecommerceuser.user.service;

import com.project.ecommerceuser.cart.cartService.CartProxy;
import com.project.ecommerceuser.product.productService.ProductProxy;
import com.project.ecommerceuser.user.entity.Address;
import com.project.ecommerceuser.user.entity.User;
import com.project.ecommerceuser.user.exception.UserNotFoundException;
import com.project.ecommerceuser.user.repository.AddressRepository;
import com.project.ecommerceuser.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartProxy cartProxy;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductProxy productProxy;

    @Override
    public User createUser(User user) throws DuplicateKeyException {
        if(existsUserByMail(user.getEmail())) {
            throw new DuplicateKeyException("User already exists");
        }
        else {
            if(user.getAddresses() != null){
                Address savedAddress =  addressRepository.save(user.getAddresses().get(0));
            }
            return userRepository.save(user);
        }
    }

    @Override
    public User findUser(String id) throws UserNotFoundException {
        User user = userRepository.findById(id).stream().findFirst().orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        else return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException{
        User user = userRepository.findByEmail(email);
        if(user == null) throw new UserNotFoundException("User not found");
        else return user;
    }

    @Override
    public User addAddress(Address address,String email) {
        if(existsUserByMail(email)){
            Address savedAddress = addressRepository.save(address);
            User user = findUserByEmail(email);
            user.getAddresses().add(savedAddress);
            return userRepository.save(user);
        }else {
            throw new UserNotFoundException("User not found Exception");
        }
    }

    private boolean existsUserByMail(String email){
        if(userRepository.findByEmail(email) == null) return false;
        return true;
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    private boolean exitsByProductId(String id) throws Exception {
        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("id",id);
        try{
            ResponseEntity<Map> responseEntity = new RestTemplate().getForEntity("http://localhost:8082/api/products/{id}/exists", Map.class,uriVariables);
        }catch (RuntimeException re){
            throw new Exception("Product not found");
        }
        return true;
    }

    @Override
    public void addToCart(String productId, String userId, double price) throws Exception {
        productProxy.existsByID(productId);
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException("User not found");
        }
        Map<String,String> request = new HashMap<>();
        request.put("product_id",productId);
        request.put("price",price+"");
        try{
            cartProxy.addToCart(userId,request);
        }catch (Exception ex){
            throw ex;
        }
    }


}
