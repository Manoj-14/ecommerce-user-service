package com.project.ecommerceuser.user.repository;

import com.project.ecommerceuser.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
