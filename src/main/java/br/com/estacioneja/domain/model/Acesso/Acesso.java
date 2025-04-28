package br.com.estacioneja.domain.model.Acesso;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
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
@Table(name="acessos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TipoAcesso tipoAcesso;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="empresaId", referencedColumnName = "id")
    private Empresa empresa;

    public Acesso(TipoAcesso tipoAcesso, Usuario usuario, Empresa empresa) {
        this.tipoAcesso = tipoAcesso;
        this.usuario = usuario;
        this.empresa = empresa;
    }
}
