package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.WalletService;
import com.example.testchatgpt.model.User;
import com.example.testchatgpt.model.Wallet;
import com.example.testchatgpt.repository.UserRepository;
import com.example.testchatgpt.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet updateWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public void deleteWallet(Long walletId) {
        walletRepository.deleteById(walletId);
    }

    @Override
    public Wallet getWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElse(null);
    }

    public void addMoneyToWallet(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Wallet wallet = user.getWallet();
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        wallet.setBalance(newBalance);

        userRepository.save(user);
    }
}
