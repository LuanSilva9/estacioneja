package br.com.estacioneja.domain.model.Registro;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import br.com.estacioneja.shared.enums.TipoRegistro;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "registro_entrada_saida")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "veiculoId", referencedColumnName = "id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "estacionamentoId", referencedColumnName = "id")
    private Estacionamento estacionamento;

    @Enumerated(EnumType.STRING)
    private TipoRegistro tipoRegistro;

    private LocalDateTime dataRegistro;
    
    public Registro(Veiculo veiculo, Estacionamento estacionamento, TipoRegistro tipoRegistro) {
        this.veiculo = veiculo;
        this.estacionamento = estacionamento;
        this.tipoRegistro = tipoRegistro;
        this.dataRegistro = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }
}
