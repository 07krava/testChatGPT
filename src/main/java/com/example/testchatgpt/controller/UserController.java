package com.example.testchatgpt.controller;

import com.example.testchatgpt.Service.HousingService;
import com.example.testchatgpt.Service.UserService;
import com.example.testchatgpt.Service.WalletService;
import com.example.testchatgpt.dto.HousingDTO;
import com.example.testchatgpt.errors.UnauthorizedUserException;
import com.example.testchatgpt.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final WalletService walletService;
    private final HousingService housingService;

    @Autowired
    public UserController(UserService userService, WalletService walletService, HousingService housingService) {
        this.walletService = walletService;
        this.userService = userService;
        this.housingService = housingService;
    }

    @PostMapping("/add")
    public ResponseEntity<HousingDTO> createHousing(@ModelAttribute HousingDTO housingDTO, @RequestParam("file") MultipartFile[] files, Principal principal) {

        // Получить имя пользователя из Principal
        String username = principal.getName();

        // Проверить, что пользователь аутентифицирован
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            HousingDTO createdHousing = housingService.createHousing(housingDTO, files);
            return ResponseEntity.ok(createdHousing);
        } catch (UnauthorizedUserException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/allUsers")
    public List<User> allUsers() {
        return userService.listUsers();
    }

    @PutMapping("/updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @PostMapping("/addMoney/{userId}")
    public ResponseEntity<String> addMoneyToWallet(@PathVariable Long userId, @RequestParam BigDecimal amount){
        try{
            walletService.addMoneyToWallet(userId, amount);
            return ResponseEntity.ok("Money added successfully to the wallet.");
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("User not found with id " + userId);
        }
    }

}

