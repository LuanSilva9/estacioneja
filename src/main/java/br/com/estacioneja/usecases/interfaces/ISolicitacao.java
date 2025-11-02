package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Solicitacao.Solicitacao;
import br.com.estacioneja.dto.actions.ResolveSolicitacaoDTO;
import br.com.estacioneja.dto.input.SolicitacaoDTO;
import br.com.estacioneja.dto.output.SolicitacaoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface ISolicitacao extends IBase<Solicitacao, UUID, SolicitacaoDTO, SolicitacaoOutputDTO> {
    void resolveSolicitacao(UUID id, ResolveSolicitacaoDTO dto);
}
