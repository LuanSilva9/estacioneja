package br.com.estacioneja.modules.estacionamento;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.shared.enums.Privacidade;
import br.com.estacioneja.shared.enums.TipoVeiculo;
import br.com.estacioneja.modules.equipamento.Equipamento;
import br.com.estacioneja.modules.vinculo.Vinculo;
import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.modules.empresa.Empresa;
import br.com.estacioneja.modules.estacionamento.dto.EstacionamentoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class  Estacionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Privacidade privacidade;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<TipoVeiculo> regraEstacionamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresaId", referencedColumnName = "id")
    @JsonBackReference("relacao-empresa-estacionamento")
    private Empresa empresa;

    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("relacao-vinculo-estacionamento")
    private List<Vinculo> vinculos;

    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("relacao-equipamento-estacionamento")
    private List<Equipamento> equipamentos;

    private Long capacidade;
    private Long capacidadeDisponivel;

    public Estacionamento(EstacionamentoDTO dto, Empresa empresa) {
        this.privacidade = dto.privacidade();
        this.descricao = dto.descricao();
        this.empresa = empresa;
        this.regraEstacionamento = dto.regraEstacionamento();
        this.capacidade = dto.capacidade();
        this.capacidadeDisponivel = dto.capacidade();
    }

    public void entrarVeiculo() {
        if (capacidadeDisponivel <= 0) {
            throw new BusinessException("Estacionamento lotado");
        }
        capacidadeDisponivel--;
    }

    public void sairVeiculo() {
        capacidadeDisponivel++;
    }
}
