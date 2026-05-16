package br.com.estacioneja.domain.model.Empresa;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.domain.enums.TipoEmpresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.exceptions.custom.BusinessException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private TipoEmpresa tipoEmpresa;

    @ManyToOne
    @JoinColumn(name = "empresaPaiId", referencedColumnName = "id", nullable = true)
    private Empresa empresaPai;

    @OneToMany(mappedBy = "empresaPai", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("relacao-empresa-filial")
    private List<Empresa> filiais;

    @ManyToOne
    @JoinColumn(name="representanteId", referencedColumnName = "id")
    private Usuario representante;

    @ManyToOne
    @JoinColumn(name="enderecoId", referencedColumnName = "id", unique = true)
    private Endereco endereco;

    private String prefixo;

    @Enumerated(EnumType.STRING)
    private Plano plano;
    
    @Column(unique = true)
    private String cnpj;

    @Column(name = "logotipo_empresa", length = 512)
    private String logotipoEmpresa;

    @Column(name = "banner_empresa", length = 512)
    private String bannerEmpresa;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("relacao-empresa-estacionamento")
    private List<Estacionamento> estacionamento;

    public static Empresa criarMatriz(Usuario representante, String nome, Endereco endereco, TipoEmpresa tipo, String cnpj, String prefixo, Plano plano ) {
        Empresa e = new Empresa();
        
        e.representante = representante;
        e.nome = nome;
        e.endereco = endereco;
        e.tipoEmpresa = tipo;
        e.cnpj = cnpj;
        e.prefixo = prefixo;
        e.plano = plano;

        return e;
    }

    public static Empresa criarFilial(String nome, Endereco endereco, TipoEmpresa tipo, String cnpj, String prefixo, Plano plano, Empresa empresaPai, Usuario representante) {
        if (empresaPai != null && empresaPai.isFilial()) {
            throw new BusinessException("Filial não pode ter filial");
        }
        
        Empresa e = new Empresa();

        e.nome = nome;
        e.endereco = endereco;
        e.tipoEmpresa = tipo;
        e.cnpj = cnpj;
        e.prefixo = prefixo;
        e.plano = plano;
        e.empresaPai = empresaPai;
        e.representante = representante;

        return e;
    }

    private boolean isFilial() {
        return this.empresaPai != null;
    }
}
