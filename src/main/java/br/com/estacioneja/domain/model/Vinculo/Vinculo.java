package br.com.estacioneja.domain.model.Vinculo;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    @JsonManagedReference("relacao-vinculo-usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estacionamentoId", referencedColumnName = "id")
    @JsonManagedReference("relacao-vinculo-estacionamento")
    private Estacionamento estacionamento;

    public Vinculo(Usuario usuario, Estacionamento estacionamento) {
        this.usuario = usuario;
        this.estacionamento = estacionamento;
    }
}
