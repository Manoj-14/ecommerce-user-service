package com.project.ecommerceuser.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@JsonIgnoreProperties({"password", "id"})
@Document(collection = "User")
public class User {

    @Id
    private String id;

    @NotNull
    private String name;

//    @JsonIgnore   // or you can use class level jsonIgnoreProperties annotations
    @NotNull
    private String password;
    @NotNull
    @Email(message = "Please enter the correct email")
    private String email;
    @DocumentReference(lazy = true)
    private List<Address> addresses;

    public User() {
    }

    public User(String name, String password, String email, List<Address> addresses) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
