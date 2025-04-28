package br.com.estacioneja.domain.model.Empresa;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.EmpresaDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="empresas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endereco;
    private TipoEmpresa tipoEmpresa;
    private String cnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="representanteId", referencedColumnName = "id")
    private Usuario representante;

    public Empresa(EmpresaDTO dto, Usuario representante) {
        this.endereco = dto.endereco();
        this.cnpj = dto.cnpj();
        this.tipoEmpresa = dto.tipoEmpresa();
        this.representante = representante;
    }
}
