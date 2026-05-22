package br.com.estacioneja.modules.estacionamento;

import java.util.List;
import java.util.UUID;

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
import br.com.estacioneja.modules.usuario.Usuario;

import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.infra.config.security.AuthorizationService;

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
        Estacionamento newEstacionamento = new Estacionamento(dto, empresa);
        Estacionamento saved = estacionamentoRepository.save(newEstacionamento);
        return estacionamentoMapper.toDto(saved);
    }

    @Transactional
    public void update(UUID id, EstacionamentoUpdateDto dto) {
        Estacionamento estacionamento = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), CARGOS_GESTAO);

        estacionamento.setPrivacidade(dto.privacidade());
        estacionamentoRepository.save(estacionamento);
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
