package br.com.estacioneja.domain.model.Conexao;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.estacioneja.domain.enums.TipoComunicacao;
import br.com.estacioneja.domain.enums.TipoProtocolo;
import br.com.estacioneja.domain.model.Equipamento.Equipamento;
import br.com.estacioneja.dto.input.ConexaoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conexoes_equipamentos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conexao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TipoComunicacao tipoComunicacao;
    
    @Enumerated(EnumType.STRING)
    private TipoProtocolo tipoProtocolo;

    private String endereco;
    private Integer porta;
    private String credenciais;
    
    private String enderecoMac;

    @OneToOne(mappedBy = "conexaoHardware")
    @JsonBackReference("relacao-conexao-equipamento")
    private Equipamento equipamento;

    public Conexao(ConexaoDTO conexaoDTO) {
        this.tipoComunicacao = conexaoDTO.tipoComunicacao();
        this.tipoProtocolo = conexaoDTO.tipoProtocolo();
        this.endereco = conexaoDTO.endereco();
        this.porta = conexaoDTO.porta();
        this.credenciais = conexaoDTO.credenciais();
        this.enderecoMac = conexaoDTO.enderecoMac();
    }
}
