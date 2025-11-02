package br.com.estacioneja.domain.model.Solicitacao;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.estacioneja.domain.enums.Situacao;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
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
    @JoinColumn(name = "filialId", referencedColumnName = "id")
    private Filial filial;

    @ManyToOne
    @JoinColumn(name = "veiculoId", referencedColumnName = "id")
    private Veiculo veiculo;
    
    private Situacao situacao;
    private LocalDateTime createdAt;

    public Solicitacao(Usuario usuario, Filial filial, Veiculo veiculo) {
        this.usuario = usuario;
        this.filial = filial;
        this.veiculo = veiculo;
        this.situacao = Situacao.PENDENTE;
        this.createdAt = LocalDateTime.now();
    }
}
