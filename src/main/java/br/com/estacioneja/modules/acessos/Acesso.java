package br.com.estacioneja.modules.acessos;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.modules.empresa.Empresa;
import br.com.estacioneja.modules.usuario.Usuario;
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

    public Acesso(TipoAcesso tipoAcesso, Usuario usuario, Empresa empresa) {
        this.tipoAcesso = tipoAcesso;
        this.usuario = usuario;
        this.empresa = empresa;
    }
}
