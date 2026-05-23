package br.com.estacioneja.modules.estacionamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.modules.empresa.Empresa;
import br.com.estacioneja.modules.equipamento.Equipamento;
import br.com.estacioneja.modules.vinculo.Vinculo;
import br.com.estacioneja.shared.enums.MetodoEntrada;
import br.com.estacioneja.shared.enums.Privacidade;
import br.com.estacioneja.shared.enums.TipoVeiculo;
import jakarta.persistence.CascadeType;
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

    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("relacao-regra-estacionamento")
    private List<RegraCapacidade> regrasCapacidade = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MetodoEntrada metodoEntrada;

    public Estacionamento(Privacidade privacidade, String descricao, Empresa empresa, MetodoEntrada metodoEntrada) {
        this.privacidade = privacidade;
        this.descricao = descricao;
        this.empresa = empresa;
        this.metodoEntrada = metodoEntrada;
        this.regrasCapacidade = new ArrayList<>();
    }

    public void addRegraCapacidade(TipoVeiculo tipoVeiculo, Long capacidade) {
        boolean jaExiste = regrasCapacidade.stream().anyMatch(r -> r.getTipoVeiculo() == tipoVeiculo);
        if (jaExiste) {
            throw new BusinessException("Já existe regra de capacidade para " + tipoVeiculo);
        }
        regrasCapacidade.add(new RegraCapacidade(this, tipoVeiculo, capacidade));
    }

    public Optional<RegraCapacidade> buscarRegra(TipoVeiculo tipoVeiculo) {
        return regrasCapacidade.stream()
                .filter(r -> r.getTipoVeiculo() == tipoVeiculo)
                .findFirst();
    }

    public void removerRegra(RegraCapacidade regra) {
        if (regra.getOcupacao() > 0) {
            throw new BusinessException(
                "Não é possível remover a regra de " + regra.getTipoVeiculo()
                + ": ainda há " + regra.getOcupacao() + " veículo(s) estacionado(s)."
            );
        }
        regrasCapacidade.remove(regra);
    }

    public void entrarVeiculo(TipoVeiculo tipoVeiculo) {
        findRegra(tipoVeiculo).entrar();
    }

    public void sairVeiculo(TipoVeiculo tipoVeiculo) {
        findRegra(tipoVeiculo).sair();
    }

    private RegraCapacidade findRegra(TipoVeiculo tipoVeiculo) {
        return buscarRegra(tipoVeiculo)
                .orElseThrow(() -> new BusinessException("Estacionamento não aceita veículos do tipo " + tipoVeiculo));
    }
}
