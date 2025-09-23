package br.com.estacioneja.domain.model.Empresa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.EmpresaDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

    private String nome;
    private TipoEmpresa tipoEmpresa;

    @Column(unique = true)
    private String cnpj;
    private String prefixo;

    @OneToOne
    @JoinColumn(name="representanteId", referencedColumnName = "id")
    private Usuario representante;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("relacao-empresa-estacionamento")
    private List<Estacionamento> estacionamentos;

    public Empresa(EmpresaDTO dto, Usuario representante) {
        this.nome = dto.nome();
        this.cnpj = dto.cnpj();
        this.tipoEmpresa = dto.tipoEmpresa();
        this.prefixo = dto.prefixo();
        this.representante = representante;
    }
}
