package br.com.estacioneja.domain.model.Filial;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.FilialDTO;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filiais")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String nome;
    
    @ManyToOne
    @JoinColumn(name="enderecoId", referencedColumnName = "id", unique = true)
    private Endereco endereco;

    private String prefixo;

    @Enumerated(EnumType.STRING)
    private Plano plano;
    
    @Column(unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("relacao-filial-estacionamento")
    private List<Estacionamento> estacionamento;

    @ManyToOne
    @JoinColumn(name = "empresaId", referencedColumnName = "id")
    @JsonBackReference("relacao-empresa-filial")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name="representanteId", referencedColumnName = "id")
    private Usuario representante;
    
    public Filial(FilialDTO dto, Endereco endereco, Usuario representante, Empresa empresa) {
        this.nome = dto.nome();
        this.cnpj = dto.cnpj();
        this.endereco = endereco;
        this.plano = dto.plano();
        this.prefixo = dto.prefixo();
        this.empresa = empresa;
        this.representante = representante;
    }
}
