package com.example.api.adapters.dto.walletDTO;


import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponseDTO(
        UUID walletId,
        UUID userId,
        BigDecimal balance
) {}
