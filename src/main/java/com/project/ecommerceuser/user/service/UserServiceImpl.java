package com.project.ecommerceuser.user.service;

import com.project.ecommerceuser.user.entity.Address;
import com.project.ecommerceuser.user.entity.User;
import com.project.ecommerceuser.user.exception.UserNotFoundException;
import com.project.ecommerceuser.user.repository.AddressRepository;
import com.project.ecommerceuser.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

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




}
