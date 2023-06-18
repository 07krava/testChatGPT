package com.example.testchatgpt.Service;

import com.example.testchatgpt.model.Wallet;

public interface WalletService {

    Wallet createWallet(Wallet wallet);

    Wallet updateWallet(Wallet wallet);

    void deleteWallet(Long walletId);

    Wallet getWalletById(Long walletId);
}
