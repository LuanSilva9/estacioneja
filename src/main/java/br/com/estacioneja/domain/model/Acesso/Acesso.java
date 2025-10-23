package br.com.estacioneja.domain.model.Acesso;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="acessos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TipoAcesso tipoAcesso;

    @ManyToOne
    @JoinColumn(name = "usuarioId", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "empresaId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "filialId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Filial filial;

    public Acesso(TipoAcesso tipoAcesso, Usuario usuario, Filial filial) {
        this.tipoAcesso = tipoAcesso;
        this.usuario = usuario;
        this.empresa = null;
        this.filial = filial;
    }

    public Acesso(TipoAcesso tipoAcesso, Usuario usuario, Empresa empresa) {
        this.tipoAcesso = tipoAcesso;
        this.usuario = usuario;
        this.empresa = empresa;
        this.filial = null;
    }
}
