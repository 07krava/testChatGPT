package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.UserService;
import com.example.testchatgpt.model.Role;
import com.example.testchatgpt.model.User;
import com.example.testchatgpt.model.Wallet;
import com.example.testchatgpt.repository.UserRepository;
import com.example.testchatgpt.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private WalletRepository walletRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public User createUser(User user, Wallet wallet) {

        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null){
            throw new RuntimeException("This user already exists!");
        }

        Wallet walletEntity = new Wallet();
        if (wallet != null) {
            walletEntity.setBalance(wallet.getBalance());
            walletEntity.setCurrency(wallet.getCurrency());
            walletEntity.setFrozenBalance(wallet.getFrozenBalance());
        }
        walletEntity.setUser(user);

        user.setWallet(walletEntity);
        user.getRoles().add(Role.USER);
        walletRepository.save(walletEntity);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + id));
       return user;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + id));
        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + id));
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setPassword(user.getPassword());
        userEntity.setRoles(user.getRoles());
        userEntity.setBookings(user.getBookings());
        userEntity.setBookings(user.getBookings());

        return userRepository.save(userEntity);
    }
}
