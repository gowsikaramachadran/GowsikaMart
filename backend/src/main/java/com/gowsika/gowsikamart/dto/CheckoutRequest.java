package com.gowsika.gowsikamart.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String paymentMethod;
}
