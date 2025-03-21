package com.example.api.application.usecases.transactions;

import com.example.api.adapters.dto.transferDTO.TransferRequestDto;
import com.example.api.application.gateways.AuthorizationGateway;
import com.example.api.application.gateways.NotificationGateway;
import com.example.api.domain.Enum.UserType;
import com.example.api.domain.exceptions.BusinessException;
import com.example.api.infrastructure.persistence.model.UserModel;
import com.example.api.infrastructure.persistence.model.WalletModel;
import com.example.api.infrastructure.persistence.repositories.UserRepository;
import com.example.api.infrastructure.persistence.repositories.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferMoneyUseCase {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final AuthorizationGateway authorizationGateway;
    private final NotificationGateway notificationGateway;

    public TransferMoneyUseCase(UserRepository userRepository, WalletRepository walletRepository,
                                AuthorizationGateway authorizationGateway, NotificationGateway notificationGateway) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.authorizationGateway = authorizationGateway;
        this.notificationGateway = notificationGateway;
    }

    @Transactional
    public void execute(UUID payerId, TransferRequestDto dto) {

        UserModel payer = userRepository.findById(payerId)
                .orElseThrow(() -> new BusinessException("Pagador não encontrado."));

        UserModel payee = userRepository.findByDocument(dto.payeeDocument())
                .orElseThrow(() -> new BusinessException("Recebedor não encontrado."));

        if (payer.getUserType() == UserType.LOGISTA) {
            throw new BusinessException("Lojistas não podem enviar dinheiro.");
        }

        WalletModel payerWallet = walletRepository.findByUserUserId(payerId)
                .orElseThrow(() -> new BusinessException("Carteira do pagador não encontrada."));

        WalletModel payeeWallet = walletRepository.findByUserUserId(payee.getUserId())
                .orElseThrow(() -> new BusinessException("Carteira do recebedor não encontrada."));

        BigDecimal payerBalance = payerWallet.getBalance();
        if (payerBalance == null || payerBalance.compareTo(dto.value()) < 0) {
            throw new BusinessException("Saldo insuficiente.");
        }

        if (!authorizationGateway.isAuthorized()) {
            throw new BusinessException("Transação não autorizada.");
        }

        BigDecimal value = dto.value();
        payerWallet.setBalance(payerWallet.getBalance().subtract(value));
        payeeWallet.setBalance(payeeWallet.getBalance().add(value));

        walletRepository.save(payerWallet);
        walletRepository.save(payeeWallet);

        notificationGateway.notify(payee.getEmail(), "Você recebeu R$ " + value + " de " + payer.getFullName());
    }
}