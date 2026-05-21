package br.com.estacioneja.shared.vo;

import br.com.estacioneja.errors.exceptions.BusinessException;

public class CPF {
    private String cpf;

    public CPF(String cpfValue) {
        cpfValue = cpfValue.trim();

        if (!isValidCpf(cpfValue)) throw new BusinessException("CPF Inválido");

        this.cpf = cpfValue;
    }

    private boolean isValidCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || isCpfComDigitosIguais(cpf)) {
            return false;
        }

        try {
            char primeiroDigito = calcularDigito(cpf, 9, 10);
            char segundoDigito = calcularDigito(cpf, 10, 11);

            return primeiroDigito == cpf.charAt(9) &&
                segundoDigito == cpf.charAt(10);

        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCpfComDigitosIguais(String cpf) {
        return cpf.chars().distinct().count() == 1;
    }

    private char calcularDigito(String cpf, int tamanho, int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;

        for (int i = 0; i < tamanho; i++) {
            int numero = cpf.charAt(i) - '0';
            soma += numero * peso--;
        }

        int resto = 11 - (soma % 11);
        return (resto >= 10) ? '0' : (char) (resto + '0');
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (isValidCpf(cpf)) throw new BusinessException("CPF Inválido");
        this.cpf = cpf;
    }
}
