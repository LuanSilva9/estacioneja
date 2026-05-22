package br.com.estacioneja.modules.vinculo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.errors.exceptions.DuplicateException;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.modules.estacionamento.Estacionamento;
import br.com.estacioneja.modules.estacionamento.EstacionamentoService;
import br.com.estacioneja.modules.security.AuthorizationService;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.veiculo.Veiculo;
import br.com.estacioneja.modules.veiculo.VeiculoService;
import br.com.estacioneja.modules.vinculo.dto.VinculoCardUsuarioDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoConsultaGuaritaDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoLinhaAdminDTO;
import br.com.estacioneja.shared.enums.TipoAcesso;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VinculoService {

    private final VinculoRepository vinculoRepository;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;
    private final VinculoMapper vinculoMapper;
    private final AuthorizationService authorizationService;

    /* TRANSAÇÕES */

    @Transactional
    public VinculoLinhaAdminDTO create(VinculoDTO dto) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaIdOf(estacionamento), TipoAcesso.MASTER);

        Veiculo veiculo = veiculoService.findByPlaca(dto.placaVeiculo());

        if (!estacionamento.getRegraEstacionamento().contains(veiculo.getTipoVeiculo())) {
            throw new BusinessException("Esse veiculo viola a regra do estacionamento");
        }

        validateDuplicate(veiculo, estacionamento);

        Vinculo vinculo = new Vinculo(estacionamento, veiculo.getUsuario(), veiculo);
        return vinculoMapper.toLinhaAdmin(vinculoRepository.save(vinculo));
    }

    @Transactional
    public void delete(UUID id) {
        Vinculo vinculo = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaIdOf(vinculo.getEstacionamento()), TipoAcesso.MASTER);

        vinculoRepository.delete(vinculo);
    }

    /* CONSULTAS */

    public Vinculo findEntityById(UUID id) {
        return vinculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vinculo não encontrado"));
    }

    public List<VinculoCardUsuarioDTO> findCardUsuarioByUsuario(Usuario usuario) {
        return vinculoMapper.toCardUsuarioList(vinculoRepository.findAllByUsuario(usuario));
    }

    public List<VinculoLinhaAdminDTO> findLinhaAdminByEmpresa(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaId, TipoAcesso.MASTER);

        return vinculoMapper.toLinhaAdminList(vinculoRepository.findByEmpresaId(empresaId));
    }

    public VinculoConsultaGuaritaDTO findConsultaGuarita(String placa, UUID estacionamentoId) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(estacionamentoId);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), TipoAcesso.MASTER, TipoAcesso.GUARITA);

        Vinculo vinculo = vinculoRepository.findByEstacionamentoIdAndVeiculoPlaca(estacionamentoId, placa)
                .orElseThrow(() -> new EntityNotFoundException("Vinculo não encontrado"));
        return vinculoMapper.toConsultaGuarita(vinculo);
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

    private UUID empresaIdOf(Estacionamento e) {
        if (e == null || e.getEmpresa() == null) return null;
        return e.getEmpresa().getId();
    }
}
