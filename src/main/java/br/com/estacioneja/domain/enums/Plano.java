package br.com.estacioneja.domain.enums;


import java.util.Arrays;
import java.util.List;

public enum Plano {
    FREE_TIER("Free Tier", 20L, Arrays.asList( "100% Gratis", "Auditoria do estacionamento", "Ótimo para estacionamentos pequenos com guarita"), 0L, false, false),
    INICIAL("Plano Inicial", 50L, Arrays.asList("Suporte durante horario comercial", "+ Segurança", "+ Controle", "Auditoria do estacionamento", "Ótimo para estacionamentos pequenos com guarita"), 500L, false, true),
    MEDIO("Plano Médio", 200L, Arrays.asList("Suporte 24/7", "+ Segurança", "+ Controle", "Auditoria do estacionamento", "Totem + Leitor NFC", "Cancela / Portão Integrado", "Ótimo para estacionamentos Maiores, Independe da guarita"), 2000L, true, true),
    AVANCADO("Plano Avançado", 500L, Arrays.asList("Suporte 24/7", "+ Segurança", "+ Controle", "Auditoria do estacionamento", "Totem + Leitor NFC", "Cancela / Portão Integrado", "Ótimo para estacionamentos Maiores, Independe da guarita"), 32000L, true, true);

    String titulo;
    Long recomendadoParaAte;
    List<String> beneficios;
    Long custoPorMes;
    Boolean estoqueAtivo;
    Boolean exibirPagina;
    
    
    private Plano(String titulo, Long recomendadoParaAte, List<String> beneficios, Long custoPorMes, Boolean estoqueAtivo, Boolean exibirPagina) {
        this.titulo = titulo;
        this.recomendadoParaAte = recomendadoParaAte;
        this.beneficios = beneficios;
        this.custoPorMes = custoPorMes;
        this.estoqueAtivo = estoqueAtivo;
        this.exibirPagina = exibirPagina;
    }

    public String getTitulo() {
        return titulo;
    }

    public Long getRecomendadoParaAte() {
        return recomendadoParaAte;
    }

    public List<String> getBeneficios() {
        return beneficios;
    }

    public Long getCustoPorMes() {
        return custoPorMes;
    }

    public Boolean getEstoqueAtivo() {
        return estoqueAtivo;
    }

    public Boolean getExibirPagina() {
        return exibirPagina;
    }
}

