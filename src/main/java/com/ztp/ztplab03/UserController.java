package com.ztp.ztplab03;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    //GET all
    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }
    //CREATE
    @PostMapping("/users")
    User createUser(@Valid @RequestBody User newUser) {
        return repository.save(newUser);
    }




}
