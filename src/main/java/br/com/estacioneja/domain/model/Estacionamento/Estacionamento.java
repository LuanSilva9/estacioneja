package br.com.estacioneja.domain.model.Estacionamento;

import java.util.UUID;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.dto.EstacionamentoDTO;
import jakarta.persistence.CascadeType;
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
    private StatusEstacionamento statusEstacionamento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="empresaId", referencedColumnName = "id")
    private Empresa empresa;

    public Estacionamento(EstacionamentoDTO dto, Empresa empresa) {
        this.capacidadeTotal = dto.capacidade();
        this.statusEstacionamento = dto.statusEstacionamento();
        this.empresa = empresa;
    }
}
