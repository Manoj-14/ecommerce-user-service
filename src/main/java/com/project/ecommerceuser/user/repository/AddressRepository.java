package com.project.ecommerceuser.user.repository;

import com.project.ecommerceuser.user.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address,String> {
}
