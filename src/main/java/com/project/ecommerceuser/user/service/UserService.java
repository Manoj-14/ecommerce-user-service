package com.project.ecommerceuser.user.service;

import com.project.ecommerceuser.user.entity.Address;
import com.project.ecommerceuser.user.entity.User;
import com.project.ecommerceuser.user.exception.UserNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {
    @Transactional
    User createUser(User user) throws DuplicateKeyException;

    User findUser(String id) throws UserNotFoundException;

    User findUserByEmail(String email) throws UserNotFoundException;

    @Transactional
    User addAddress(Address address,String email);
}
