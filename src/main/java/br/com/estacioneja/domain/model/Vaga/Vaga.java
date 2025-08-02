package br.com.estacioneja.domain.model.Vaga;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Reserva.Reserva;
import br.com.estacioneja.dto.input.VagaDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JsonManagedReference("relacao-vaga-estacionamento")
    private Estacionamento estacionamento;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("relacao-reserva-vaga")
    private List<Reserva> reservaLogs;

    private StatusVaga statusVaga;

    public Vaga(VagaDTO dto, Estacionamento estacionamento) {
        this.tipoVaga = dto.tipoVaga();
        this.slug = dto.slug();
        this.estacionamento = estacionamento;
    }

    public Vaga(Estacionamento estacionamento, String slug) {
        this.estacionamento = estacionamento;
        this.slug = slug;
        this.tipoVaga = TipoVaga.ANY;
        this.statusVaga = StatusVaga.LIVRE;
    }
}