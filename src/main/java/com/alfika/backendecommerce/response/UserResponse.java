package com.alfika.backendecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@AllArgsConstructor
public class UserResponse {

    private String message;
    private HashMap<String, String> map;
}
