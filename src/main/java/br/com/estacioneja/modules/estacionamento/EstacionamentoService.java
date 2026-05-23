package br.com.estacioneja.modules.estacionamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.estacioneja.shared.enums.TipoVeiculo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.shared.enums.Privacidade;
import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.modules.empresa.Empresa;
import br.com.estacioneja.modules.empresa.EmpresaService;
import br.com.estacioneja.modules.estacionamento.dto.EstacionamentoDTO;
import br.com.estacioneja.modules.estacionamento.dto.EstacionamentoOutputDTO;
import br.com.estacioneja.modules.estacionamento.dto.EstacionamentoUpdateDto;
import br.com.estacioneja.modules.estacionamento.dto.RegraCapacidadeInputDTO;
import br.com.estacioneja.modules.security.AuthorizationService;
import br.com.estacioneja.modules.usuario.Usuario;

import br.com.estacioneja.errors.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {
    private final EstacionamentoRepository estacionamentoRepository;
    private final EmpresaService empresaService;
    private final EstacionamentoMapper estacionamentoMapper;
    private final AuthorizationService authorizationService;

    private static final TipoAcesso[] CARGOS_GESTAO = { TipoAcesso.MASTER, TipoAcesso.CADASTRO_GESTAO };

    /* TRANSACOES */

    @Transactional
    public EstacionamentoOutputDTO create(EstacionamentoDTO dto) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, dto.empresaId(), CARGOS_GESTAO);

        Empresa empresa = empresaService.findEntityById(dto.empresaId());
        Estacionamento newEstacionamento = new Estacionamento(dto.privacidade(), dto.descricao(), empresa, dto.metodoEntrada());
        for (RegraCapacidadeInputDTO regra : dto.regrasCapacidade()) {
            newEstacionamento.addRegraCapacidade(regra.tipoVeiculo(), regra.capacidade());
        }
        Estacionamento saved = estacionamentoRepository.save(newEstacionamento);
        return estacionamentoMapper.toDto(saved);
    }

    @Transactional
    public void update(UUID id, EstacionamentoUpdateDto dto) {
        Estacionamento estacionamento = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), CARGOS_GESTAO);

        estacionamento.setPrivacidade(dto.privacidade());
        estacionamento.setDescricao(dto.descricao());
        estacionamento.setMetodoEntrada(dto.metodoEntrada());
        mergeRegrasCapacidade(estacionamento, dto.regrasCapacidade());

        estacionamentoRepository.save(estacionamento);
    }

    private void mergeRegrasCapacidade(Estacionamento estacionamento, List<RegraCapacidadeInputDTO> regrasDto) {
        Set<TipoVeiculo> tiposDesejados = regrasDto.stream()
                .map(RegraCapacidadeInputDTO::tipoVeiculo)
                .collect(Collectors.toSet());

        List<RegraCapacidade> aRemover = estacionamento.getRegrasCapacidade().stream()
                .filter(r -> !tiposDesejados.contains(r.getTipoVeiculo()))
                .collect(Collectors.toCollection(ArrayList::new));
        for (RegraCapacidade regra : aRemover) {
            estacionamento.removerRegra(regra);
        }

        for (RegraCapacidadeInputDTO regraDto : regrasDto) {
            estacionamento.buscarRegra(regraDto.tipoVeiculo())
                    .ifPresentOrElse(
                            existente -> existente.ajustarCapacidade(regraDto.capacidade()),
                            () -> estacionamento.addRegraCapacidade(regraDto.tipoVeiculo(), regraDto.capacidade())
                    );
        }
    }

    @Transactional
    public void delete(UUID id) {
        Estacionamento estacionamento = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), CARGOS_GESTAO);

        estacionamentoRepository.delete(estacionamento);
    }

    private UUID empresaIdOf(Estacionamento e) {
        return e.getEmpresa() == null ? null : e.getEmpresa().getId();
    }


    /* CONSULTAS */

    @Transactional(readOnly = true)
    public List<EstacionamentoOutputDTO> findByPrivacidade(Privacidade privacidade) {
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findByPrivacidade(privacidade));
    }

    @Transactional(readOnly = true)
    public List<EstacionamentoOutputDTO> findByEmpresa(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaAccess(autenticado, empresaId);

        Empresa empresa = empresaService.findEntityById(empresaId);
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findAllByEmpresa(empresa));
    }

    @Transactional(readOnly = true)
    public EstacionamentoOutputDTO findById(UUID idEstacionamento) {
        Estacionamento estacionamento = findEntityById(idEstacionamento);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaAccess(autenticado, empresaIdOf(estacionamento));

        return estacionamentoMapper.toDto(estacionamento);
    }

    @Transactional(readOnly = true)
    public Estacionamento findEntityById(UUID idEstacionamento) {
       return estacionamentoRepository.findById(idEstacionamento).orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado")); 
    }

    @Transactional(readOnly = true)
    public Estacionamento findEntityLocked(UUID idEstacionamento) {
        return estacionamentoRepository.findByIdForUpdate(idEstacionamento).orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado"));
    }

}
