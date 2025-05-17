package br.com.estacioneja.domain.model.Veiculo;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.i.VeiculoDTO;
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
@Table(name="veiculos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String modelo;
    private String cor;
    private String placa;
    private TipoVeiculo tipoVeiculo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="proprietarioId", referencedColumnName = "id")
    @JsonManagedReference("relacao-usuario-veiculo")
    private Usuario proprietario;


    public Veiculo(VeiculoDTO dto, Usuario proprietario) {
        this.modelo = dto.modelo();
        this.cor = dto.cor();
        this.placa = dto.placa();
        this.proprietario = proprietario;
    }
}
