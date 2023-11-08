package com.project.ecommerceuser.cart.entiry;

import java.util.Date;

public class Order {
    private String _id;
    private Date createdAt;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "_id='" + _id + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
