package br.com.estacioneja.domain.model.Empresa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.EmpresaDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private String nome;
    private TipoEmpresa tipoEmpresa;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("relacao-empresa-filial")
    private List<Filial> filiais;

    @ManyToOne
    @JoinColumn(name="representanteMasterId", referencedColumnName = "id")
    private Usuario representanteMaster;

    public Empresa(EmpresaDTO dto, Usuario representanteMaster) {
        this.nome = dto.nome();
        this.tipoEmpresa = dto.tipoEmpresa();
        this.representanteMaster = representanteMaster;
    }
}
