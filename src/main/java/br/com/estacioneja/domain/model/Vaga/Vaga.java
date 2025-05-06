package br.com.estacioneja.domain.model.Vaga;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.VagaDTO;
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

    private String slug;

    @ManyToOne
    @JoinColumn(name="estacionamentoId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Estacionamento estacionamento;

    public Vaga(VagaDTO dto, Estacionamento estacionamento) {
        this.tipoVaga = dto.tipoVaga();
        this.slug = dto.slug();
        this.estacionamento = estacionamento;
    }

    public Vaga(Estacionamento estacionamento, String slug) {
        this.estacionamento = estacionamento;
        this.slug = slug;
        this.tipoVaga = TipoVaga.ANY;
    }
}