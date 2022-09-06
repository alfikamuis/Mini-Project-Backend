package com.alfika.backendecommerce.configuration;

public class Validation {

    //for image
    public static boolean isImageFile(String input) {
        if (input != null && input != "") {
            input = input.toLowerCase();
            if (input.endsWith(".png") || input.endsWith(".jpg") || input.endsWith(".jpeg") || input.endsWith(".gif")) {
                return true;
            }
        }
        return false;
    }

}
