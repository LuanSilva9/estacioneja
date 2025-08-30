package br.com.estacioneja.domain.model.Estacionamento;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
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

    private StatusEstacionamento statusEstacionamento;

    private String prefixo;

    @ManyToOne
    @JoinColumn(name="empresaId", referencedColumnName = "id")
    @JsonManagedReference("relacao-empresa-estacionamento")
    private Empresa empresa;

    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("relacao-vinculo-estacionamento")
    private List<Vinculo> vinculos;
    
    private Long capacidade;

    public Estacionamento(EstacionamentoDTO dto, Empresa empresa) {
        this.statusEstacionamento = dto.statusEstacionamento();
        this.prefixo = dto.prefixo();
        this.empresa = empresa;
        this.capacidade = dto.capacidade();
    }
}
