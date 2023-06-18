package com.example.testchatgpt.controller;

import com.example.testchatgpt.Service.UserService;
import com.example.testchatgpt.model.User;
import com.example.testchatgpt.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/addUser")
//    public User createUser(@RequestBody User user) {
//        Wallet wallet = user.getWallet();
//        User userEntity = userService.createUser(user, wallet);
//        return userEntity;
//    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/allUsers")
    public List<User> allUsers() {
        return userService.listUsers();
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}

