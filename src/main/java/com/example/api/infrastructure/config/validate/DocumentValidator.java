package com.example.api.infrastructure.config.validate;

import java.util.regex.Pattern;

public class DocumentValidator {

    private static final Pattern CPF_PATTERN = Pattern.compile("^(\\d{3}\\.?){3}-?\\d{2}$");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}$");

    public static boolean isValidCPF(String cpf) {
        return cpf != null && CPF_PATTERN.matcher(cpf).matches();
    }

    public static boolean isValidCNPJ(String cnpj) {
        return cnpj != null && CNPJ_PATTERN.matcher(cnpj).matches();
    }
}
