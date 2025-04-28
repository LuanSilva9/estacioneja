package br.com.estacioneja.domain.model.Vaga;

import java.util.UUID;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.VagaDTO;
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
@Table(name="vagas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private TipoVaga tipoVaga;

    private Long slug;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="estacionamentoId", referencedColumnName = "id")
    private Estacionamento estacionamento;

    public Vaga(VagaDTO dto, Estacionamento estacionamento) {
        this.tipoVaga = dto.tipoVaga();
        this.slug = dto.slug();
        this.estacionamento = estacionamento;
    }
}