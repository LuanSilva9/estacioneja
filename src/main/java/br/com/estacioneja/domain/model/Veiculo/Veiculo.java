package br.com.estacioneja.domain.model.Veiculo;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoVeiculo;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.VeiculoDTO;
import jakarta.persistence.Column;
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
@Table(name = "veiculos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String placa;

    private String modelo;
    private String cor;

    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    @ManyToOne
    @JoinColumn(name = "proprietarioId", referencedColumnName = "id")
    private Usuario usuario;
    
    public Veiculo(VeiculoDTO dto, Usuario proprietario) {
        this.placa = dto.placa();
        this.modelo = dto.modelo();
        this.cor = dto.cor();
        this.tipoVeiculo = dto.tipoVeiculo();
        this.usuario = proprietario;
    }

}
