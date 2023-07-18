package com.example.testchatgpt.Service;

import com.example.testchatgpt.dto.WalletDTO;
import com.example.testchatgpt.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    List<User> listUsers();

    void deleteUser(Long id);

    User updateUser(Long id, User user);
}
