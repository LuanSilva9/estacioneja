package br.com.estacioneja.services.Vinculo;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.repository.Vinculo.VinculoRepository;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.errors.exceptions.DuplicateException;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.VinculoMapper;
import br.com.estacioneja.infra.config.security.AuthorizationService;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.services.Veiculo.VeiculoService;
import br.com.estacioneja.usecases.interfaces.IVinculo;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class VinculoService implements IVinculo {

    private final VinculoRepository vinculoRepository;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;
    private final VinculoMapper vinculoMapper;
    private final AuthorizationService authorizationService;

    /* TRANSAÇÕES */

    @Override
    @Transactional
    public VinculoOutputDTO create(VinculoDTO dto) {
        Estacionamento estacionamento = getEstacionamento(dto.estacionamentoId());

        Usuario autenticado = authorizationService.getCurrentUser();

        authorizationService.requireEmpresaRole(autenticado, empresaIdOf(estacionamento), TipoAcesso.MASTER);
        
        Veiculo veiculo = getVeiculo(dto.placaVeiculo());
        
        if(!estacionamento.getRegraEstacionamento().contains(veiculo.getTipoVeiculo())) {
            throw new BusinessException("Esse veiculo viola a regra do estacionamento");
        }

        validateDuplicate(veiculo, estacionamento);

        Vinculo vinculo = new Vinculo(estacionamento, veiculo.getUsuario(), veiculo);
        
        return vinculoMapper.toDto(vinculoRepository.save(vinculo));
    }

    @Override
    @Transactional
    public void update(UUID id, VinculoDTO dto) {
        Vinculo vinculo = findEntityById(id);
        Estacionamento destino = getEstacionamento(dto.estacionamentoId());

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaIdOf(vinculo.getEstacionamento()), TipoAcesso.MASTER);
        authorizationService.requireEmpresaRole(autenticado, empresaIdOf(destino), TipoAcesso.MASTER);

        vinculo.setEstacionamento(destino);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Vinculo vinculo = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaIdOf(vinculo.getEstacionamento()), TipoAcesso.MASTER);

        vinculoRepository.delete(vinculo);
    }

    private UUID empresaIdOf(Estacionamento e) {
        if (e == null || e.getEmpresa() == null) return null;
        return e.getEmpresa().getId();
    }

    /* CONSULTAS */

    @Override
    public Vinculo findEntityById(UUID id) {
        return vinculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vinculo não encontrado"));
    }

    @Override
    public VinculoOutputDTO findById(UUID id) {
        return vinculoMapper.toDto(findEntityById(id));
    }

    @Override
    public List<VinculoOutputDTO> findVincleByUser(Usuario usuario) {
        return vinculoMapper.toDtoList(vinculoRepository.findAllByUsuario(usuario));
    }

    @Override
    public List<VinculoOutputDTO> findVincleByEmpresa(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaId, TipoAcesso.MASTER);

        return vinculoMapper.toDtoList(vinculoRepository.findByEmpresaId(empresaId));
    }

    public VinculoOutputDTO findVincleByPlacaAndEstacionamentoId(String placa, UUID estacionamentoId) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(estacionamentoId);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), TipoAcesso.MASTER, TipoAcesso.GUARITA);

        Vinculo vinculo = vinculoRepository.findByEstacionamentoIdAndVeiculoPlaca(estacionamentoId, placa)
                .orElseThrow(() -> new EntityNotFoundException("Vinculo não encontrado"));
        return vinculoMapper.toDto(vinculo);
    }
    
    /* VALIDAÇÕES */
    
    public Boolean existsVinculo(String placa, UUID estacionamentoId) {
        return vinculoRepository.existsByEstacionamentoIdAndVeiculoPlaca(estacionamentoId, placa);
    }

    private void validateDuplicate(Veiculo veiculo, Estacionamento estacionamento) {
        if (vinculoRepository.existsByEstacionamentoIdAndVeiculoPlaca(estacionamento.getId(), veiculo.getPlaca())) {
            throw new DuplicateException("Esse veículo já está vinculado nesse estacionamento");
        }
    }

    /* HELPERS */

    private Veiculo getVeiculo(String placa) {
        return veiculoService.findByPlaca(placa);
    }

    private Estacionamento getEstacionamento(UUID id) {
        return estacionamentoService.findEntityById(id);
    }
}