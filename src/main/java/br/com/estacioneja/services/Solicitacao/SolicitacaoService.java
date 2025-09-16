package br.com.estacioneja.services.Solicitacao;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Estacionamento.StatusEstacionamento;
import br.com.estacioneja.domain.model.Solicitacao.Situacao;
import br.com.estacioneja.domain.model.Solicitacao.Solicitacao;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Solicitacao.SolicitacaoRepository;
import br.com.estacioneja.dto.input.SolicitacaoDTO;
import br.com.estacioneja.dto.output.SolicitacaoOutputDTO;
import br.com.estacioneja.exceptions.custom.ParkIsPublicException;
import br.com.estacioneja.exceptions.custom.SolicitationNotFoundException;
import br.com.estacioneja.infra.config.mapper.SolicitacaoMapper;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.ISolicitacao;
import jakarta.transaction.Transactional;

@Service
public class SolicitacaoService implements ISolicitacao {
    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioService usuarioService;
    private final EstacionamentoService estacionamentoService;
    private final SolicitacaoMapper solicitacaoMapper;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, UsuarioService usuarioService, EstacionamentoService estacionamentoService, SolicitacaoMapper solicitacaoMapper) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioService = usuarioService;
        this.estacionamentoService = estacionamentoService;
        this.solicitacaoMapper = solicitacaoMapper;
    }

    /* TRANSACOES */

    @Override @Transactional
    public SolicitacaoOutputDTO create(SolicitacaoDTO dto) {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());

        if(estacionamento.getStatusEstacionamento().equals(StatusEstacionamento.PUBLICO)) throw new ParkIsPublicException();

        Solicitacao novaSolicitacao = new Solicitacao(usuario, estacionamento, Situacao.PENDENTE);

        return solicitacaoMapper.toDto(solicitacaoRepository.save(novaSolicitacao));
    }
    
    @Override @Transactional
    public SolicitacaoOutputDTO update(UUID id, SolicitacaoDTO dto) {
        Solicitacao solicitacao = findEntityById(id);

        solicitacao.setSituacao(dto.situacao());

        return this.solicitacaoMapper.toDto(this.solicitacaoRepository.save(solicitacao));
    }

    @Override @Transactional
    public void delete(UUID id) {
        Solicitacao solicitacao = findEntityById(id);

        this.solicitacaoRepository.delete(solicitacao);
    }
    
    /* CONSULTAS */

    @Override
    public Solicitacao findEntityById(UUID id) {
       return this.solicitacaoRepository.findById(id).orElseThrow(SolicitationNotFoundException::new);
    }

    @Override
    public SolicitacaoOutputDTO findById(UUID id) {
        return this.solicitacaoMapper.toDto(findEntityById(id));
    }


    
}
