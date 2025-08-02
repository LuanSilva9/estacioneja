package br.com.estacioneja.domain.model.Reserva;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.dto.input.ReservaDTO;
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
@Table(name="reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private ZonedDateTime horarioEntrada;
    private ZonedDateTime horarioSaida;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    @JsonManagedReference("relacao-reserva-usuario")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "vagaId", referencedColumnName = "id")
    @JsonManagedReference("relacao-reserva-vaga")
    private Vaga vaga;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "veiculoId", referencedColumnName = "id")
    private Veiculo veiculo;

    private ReservaStatus status;
    
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public Reserva(ReservaDTO dto, Usuario usuario, Vaga vaga) {
        this.horarioEntrada = dto.horarioEntrada();
        this.horarioSaida = dto.horarioSaida();
        this.usuario = usuario;
        this.vaga = vaga;
        
        this.status = ReservaStatus.SCHEDULED;
        this.createdAt = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }
}
