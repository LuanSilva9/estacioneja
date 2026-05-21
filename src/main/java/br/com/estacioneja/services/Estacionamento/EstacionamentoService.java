package br.com.estacioneja.services.Estacionamento;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.domain.enums.Privacidade;
import br.com.estacioneja.domain.enums.TipoAcesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.dto.update.EstacionamentoUpdateDto;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.EstacionamentoMapper;
import br.com.estacioneja.infra.config.security.AuthorizationService;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.usecases.interfaces.IEstacionamento;

@Service
@RequiredArgsConstructor
public class EstacionamentoService implements IEstacionamento {
    private final EstacionamentoRepository estacionamentoRepository;
    private final EmpresaService empresaService;
    private final EstacionamentoMapper estacionamentoMapper;
    private final AuthorizationService authorizationService;

    private static final TipoAcesso[] CARGOS_GESTAO = { TipoAcesso.MASTER, TipoAcesso.CADASTRO_GESTAO };

    /* TRANSACOES */

    @Override @Transactional
    public EstacionamentoOutputDTO create(EstacionamentoDTO dto) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, dto.empresaId(), CARGOS_GESTAO);

        Empresa empresa = empresaService.findEntityById(dto.empresaId());
        Estacionamento newEstacionamento = new Estacionamento(dto, empresa);
        Estacionamento saved = estacionamentoRepository.save(newEstacionamento);
        return estacionamentoMapper.toDto(saved);
    }

    @Override @Transactional
    public void update(UUID id, EstacionamentoUpdateDto dto) {
        Estacionamento estacionamento = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), CARGOS_GESTAO);

        estacionamento.setPrivacidade(dto.privacidade());
        estacionamentoRepository.save(estacionamento);
    }

    @Override @Transactional
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

    @Override @Transactional(readOnly = true)
    public List<EstacionamentoOutputDTO> findByPrivacidade(Privacidade privacidade) {
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findByPrivacidade(privacidade));
    }

    @Override @Transactional(readOnly = true)
    public List<EstacionamentoOutputDTO> findByEmpresa(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaAccess(autenticado, empresaId);

        Empresa empresa = empresaService.findEntityById(empresaId);
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findAllByEmpresa(empresa));
    }

    @Override @Transactional(readOnly = true)
    public EstacionamentoOutputDTO findById(UUID idEstacionamento) {
        Estacionamento estacionamento = findEntityById(idEstacionamento);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaAccess(autenticado, empresaIdOf(estacionamento));

        return estacionamentoMapper.toDto(estacionamento);
    }

    @Override @Transactional(readOnly = true)
    public Estacionamento findEntityById(UUID idEstacionamento) {
       return estacionamentoRepository.findById(idEstacionamento).orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado")); 
    }

    @Override @Transactional(readOnly = true)
    public Estacionamento findEntityLocked(UUID idEstacionamento) {
        return estacionamentoRepository.findByIdForUpdate(idEstacionamento).orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado"));
    }

}
