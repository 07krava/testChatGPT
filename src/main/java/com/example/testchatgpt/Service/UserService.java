package com.example.testchatgpt.Service;

import com.example.testchatgpt.model.User;
import com.example.testchatgpt.model.Wallet;

import java.util.List;

public interface UserService {

    User createUser(User user, Wallet wallet);

    User getUserById(Long id);

    List<User> listUsers();

    void deleteUser(Long id);

    User updateUser(Long id, User user);
}
