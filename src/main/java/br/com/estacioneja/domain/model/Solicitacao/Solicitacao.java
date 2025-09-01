package br.com.estacioneja.domain.model.Solicitacao;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "solicitacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;    

    @ManyToOne
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estacionamentoId", referencedColumnName = "id")
    private Estacionamento estacionamento;
    
    private Situacao situacao;
    private LocalDateTime createdAt;

    public Solicitacao(Usuario usuario, Estacionamento estacionamento, Situacao situacao) {
        this.usuario = usuario;
        this.estacionamento = estacionamento;
        this.situacao = situacao;
        this.createdAt = LocalDateTime.now();
    }
}
