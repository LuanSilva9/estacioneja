package br.com.estacioneja.domain.model.Vinculo;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.modules.estacionamento.Estacionamento;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.veiculo.Veiculo;
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
@Table(name = "vinculos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Vinculo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "estacionamentoId", referencedColumnName = "id")
    @JsonManagedReference("relacao-vinculo-estacionamento")
    private Estacionamento estacionamento;

    @ManyToOne
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    @JsonManagedReference("relacao-vinculo-usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "veiculoId", referencedColumnName = "id")
    @JsonManagedReference("relacao-vinculo-veiculo")
    private Veiculo veiculo;

    public Vinculo(Estacionamento estacionamento, Usuario usuario, Veiculo veiculo) {
        this.estacionamento = estacionamento;
        this.usuario = usuario;
        this.veiculo = veiculo;
    }
}
