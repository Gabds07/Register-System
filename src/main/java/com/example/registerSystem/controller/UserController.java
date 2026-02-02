package com.example.registerSystem.controller;

import com.example.registerSystem.domain.User;
import com.example.registerSystem.domain.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @PostMapping
    public User createUser(@Validated @RequestBody User user) {
        Example<User> example = Example.of(user);
        if(DataController.validateEmail(user.getEmail()) && !DataController.userExists(example, repository)) {
            return repository.save(user);
        } else {
            throw new RuntimeException("Invalid email or user already exists.");
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Validated @RequestBody User updatedUser) {
        return repository.findById(id).map( user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return repository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
