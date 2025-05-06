package br.com.estacioneja.domain.model.Estacionamento;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.dto.EstacionamentoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="estacionamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Estacionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long capacidadeTotal;
    private Long vagasDisponiveis;
    private StatusEstacionamento statusEstacionamento;

    private String prefixo;

    @ManyToOne
    @JoinColumn(name="empresaId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Empresa empresa;

    public Estacionamento(EstacionamentoDTO dto, Empresa empresa) {
        this.capacidadeTotal = dto.capacidade();
        this.statusEstacionamento = dto.statusEstacionamento();
        this.prefixo = dto.prefixo();
        this.vagasDisponiveis = capacidadeTotal;
        this.empresa = empresa;
    }
}
