package br.com.estacioneja.domain.model.Estacionamento;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.domain.enums.Privacidade;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="estacionamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Estacionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;

    private Privacidade privacidade;

    @Enumerated(EnumType.STRING)
    private Plano plano;

    private String prefixo;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "enderecoId", referencedColumnName = "id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name="empresaId", referencedColumnName = "id")
    @JsonManagedReference("relacao-empresa-estacionamento")
    private Empresa empresa;

    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("relacao-vinculo-estacionamento")
    private List<Vinculo> vinculos;
    
    private Long capacidade;

    public Estacionamento(EstacionamentoDTO dto, Endereco endereco, Empresa empresa) {
        this.privacidade = dto.privacidade();
        this.descricao = dto.descricao();
        this.prefixo = dto.prefixo();
        this.empresa = empresa;
        this.capacidade = dto.capacidade();
        this.plano = dto.plano();
        this.endereco = endereco;
    }
}
