package br.com.estacioneja.domain.enums;


import java.util.Arrays;
import java.util.List;

public enum Plano {
    INICIAL("Plano Inicial", 50L, Arrays.asList("Suporte durante horario comercial", "+ Segurança", "+ Controle", "Auditoria do estacionamento", "Ótimo para estacionamentos pequenos com guarita"), 500L, false),
    MEDIO("Plano Médio", 200L, Arrays.asList("Suporte 24/7", "+ Segurança", "+ Controle", "Auditoria do estacionamento", "Totem + Leitor NFC", "Cancela / Portão Integrado", "Ótimo para estacionamentos Maiores, Independe da guarita"), 2000L, true),
    AVANCADO("Plano Avançado", 500L, Arrays.asList("Suporte 24/7", "+ Segurança", "+ Controle", "Auditoria do estacionamento", "Totem + Leitor NFC", "Cancela / Portão Integrado", "Ótimo para estacionamentos Maiores, Independe da guarita"), 4000L, true);

    String titulo;
    Long recomendadoParaAte;
    List<String> beneficios;
    Long custoPorMes;
    Boolean estoqueAtivo;
    
    private Plano(String titulo, Long recomendadoParaAte, List<String> beneficios, Long custoPorMes, Boolean estoqueAtivo) {
        this.titulo = titulo;
        this.recomendadoParaAte = recomendadoParaAte;
        this.beneficios = beneficios;
        this.custoPorMes = custoPorMes;
        this.estoqueAtivo = estoqueAtivo;
    }
}

