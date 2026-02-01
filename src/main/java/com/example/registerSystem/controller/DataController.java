package com.example.registerSystem.controller;

import com.example.registerSystem.domain.User;
import com.example.registerSystem.domain.UserRepository;
import org.springframework.data.domain.Example;

import java.util.Objects;
import java.util.regex.Pattern;

public class DataController {

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        return email != null && pattern.matcher(email).matches();
    }

    public static boolean userExists(Example<User> example, UserRepository repository) {
        return repository.exists(example);
    }
}
