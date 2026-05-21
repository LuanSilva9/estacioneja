package br.com.estacioneja.domain.model.Equipamento;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.shared.enums.TipoEquipamento;
import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.input.EquipamentoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "equipamentos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String nome;
    private String descricao;
    private String modelo;

    @Enumerated(EnumType.STRING)
    private TipoEquipamento tipoEquipamento;
    
    @ManyToOne
    @JoinColumn(name = "estacionamentoId", referencedColumnName = "id")
    private Estacionamento estacionamento;

    @OneToOne
    @JoinColumn(name = "conexaoId", referencedColumnName = "id")
    @JsonManagedReference("relacao-conexao-equipamento")
    private Conexao conexaoHardware;

    private Boolean ativo;

    public Equipamento(EquipamentoDTO dto, Estacionamento estacionamento, Conexao conexaoHardware) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.modelo = dto.modelo();
        this.tipoEquipamento = dto.tipoEquipamento();
        this.estacionamento = estacionamento;
        this.conexaoHardware = conexaoHardware;
        this.ativo = true;
    }
}
