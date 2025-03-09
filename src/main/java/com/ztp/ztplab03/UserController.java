package com.ztp.ztplab03;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    //GET
    @GetMapping("/users/{id}")
    User getStation(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(()->new UserNotFoundException());
    }
    //UPDATE
    //update
    @PutMapping("/users/{id}")
    User updateStation(@Valid @RequestBody User newUser, @PathVariable Integer id) {
        return repository.findById(id).map(
                user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return repository.save(user);
                }
        ).orElseThrow(() -> new UserNotFoundException());
    }
    //DELETE
    @DeleteMapping("/users/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
       if(repository.existsById(id)) {
           repository.deleteById(id);
           return ResponseEntity.noContent().build();
       }
       else {
           return ResponseEntity.notFound().build();
       }
    }


}
