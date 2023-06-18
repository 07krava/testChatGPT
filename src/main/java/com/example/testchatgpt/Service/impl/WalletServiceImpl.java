package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.WalletService;
import com.example.testchatgpt.model.Wallet;
import com.example.testchatgpt.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
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
}
