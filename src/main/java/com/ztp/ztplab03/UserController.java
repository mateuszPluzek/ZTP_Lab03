package com.ztp.ztplab03;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
    ResponseEntity<List<User>> all(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String name
    ) {
        Pageable pageable = PageRequest.of(offset / limit, limit);

        Specification<User> spec = (root, query, criteriaBuilder) -> {
            if(name != null && !name.isEmpty()) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
            return null;
        };
        // Fetch the users based on the specification and pagination
        Page<User> userPage = repository.findAll(spec, pageable);

        // Return the list of users or an empty list if no users are found
        List<User> users = userPage.getContent();
        return ResponseEntity.ok(users);
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
