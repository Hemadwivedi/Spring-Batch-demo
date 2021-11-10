package com.example.springbatchdemo.controller;

import com.example.springbatchdemo.UserNotFoundException;
import com.example.springbatchdemo.model.User;
import com.example.springbatchdemo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User user) {
        return repository.save(user);
    }

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User one(@PathVariable Integer id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @PutMapping("/users/{id}")
    public User replaceEmployee(@RequestBody User newUser, @PathVariable Integer id) {
        return repository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setDept(newUser.getDept());
                    user.setSalary(newUser.getSalary());
                    user.setTime(newUser.getTime());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
