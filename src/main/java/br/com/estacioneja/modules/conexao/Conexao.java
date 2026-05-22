package br.com.estacioneja.modules.conexao;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.estacioneja.modules.equipamento.Equipamento;
import br.com.estacioneja.shared.enums.TipoComunicacao;
import br.com.estacioneja.shared.enums.TipoProtocolo;
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

    public Conexao(TipoComunicacao tipoComunicacao, TipoProtocolo tipoProtocolo, String endereco, Integer porta, String credenciais, String enderecoMac) {
        this.tipoComunicacao = tipoComunicacao;
        this.tipoProtocolo = tipoProtocolo;
        this.endereco = endereco;
        this.porta = porta;
        this.credenciais = credenciais;
        this.enderecoMac = enderecoMac;
    }
}
