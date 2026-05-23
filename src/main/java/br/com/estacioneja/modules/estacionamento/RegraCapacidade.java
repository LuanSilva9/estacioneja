package br.com.estacioneja.modules.estacionamento;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.shared.enums.TipoVeiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "regras_capacidade",
    uniqueConstraints = @UniqueConstraint(columnNames = {"estacionamentoId", "tipoVeiculo"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegraCapacidade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacionamentoId", referencedColumnName = "id")
    @JsonBackReference("relacao-regra-estacionamento")
    private Estacionamento estacionamento;

    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    private Long capacidade;

    private Long capacidadeDisponivel;

    public RegraCapacidade(Estacionamento estacionamento, TipoVeiculo tipoVeiculo, Long capacidade) {
        this.estacionamento = estacionamento;
        this.tipoVeiculo = tipoVeiculo;
        this.capacidade = capacidade;
        this.capacidadeDisponivel = capacidade;
    }

    public void entrar() {
        if (capacidadeDisponivel <= 0) {
            throw new BusinessException("Sem vagas disponíveis para " + tipoVeiculo);
        }
        capacidadeDisponivel--;
    }

    public void sair() {
        if (capacidadeDisponivel < capacidade) {
            capacidadeDisponivel++;
        }
    }

    public long getOcupacao() {
        return capacidade - capacidadeDisponivel;
    }

    public void ajustarCapacidade(Long novaCapacidade) {
        long ocupacao = getOcupacao();
        if (novaCapacidade < ocupacao) {
            throw new BusinessException(
                "Não é possível reduzir a capacidade de " + tipoVeiculo + " para " + novaCapacidade
                + ": já existem " + ocupacao + " veículo(s) estacionado(s)."
            );
        }
        this.capacidade = novaCapacidade;
        this.capacidadeDisponivel = novaCapacidade - ocupacao;
    }
}
