package com.example.api.adapters.dto.transferDTO;

import java.math.BigDecimal;

public record TransferRequestDto(
        BigDecimal value,
        String payeeDocument
) {}