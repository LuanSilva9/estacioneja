package br.com.estacioneja.modules.registro;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.errors.exceptions.ForbiddenException;
import br.com.estacioneja.modules.estacionamento.Estacionamento;
import br.com.estacioneja.modules.estacionamento.EstacionamentoService;
import br.com.estacioneja.modules.registro.dto.RegistroDTO;
import br.com.estacioneja.modules.registro.dto.RegistroOutputDTO;
import br.com.estacioneja.modules.security.AuthorizationService;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.veiculo.Veiculo;
import br.com.estacioneja.modules.veiculo.VeiculoService;
import br.com.estacioneja.modules.vinculo.VinculoService;
import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.shared.enums.TipoRegistro;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroService {
    private final RegistroRepository registroRepository;
    private final RegistroMapper registroMapper;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;
    private final VinculoService vinculoService;
    private final AuthorizationService authorizationService;

    private static final TipoAcesso[] CARGOS_OPERACAO = { TipoAcesso.MASTER, TipoAcesso.GUARITA };

    /* TRANSACOES */

    @Transactional
    public RegistroOutputDTO create(RegistroDTO dto) {
        Estacionamento estacionamento = estacionamentoService.findEntityLocked(dto.estacionamentoId());

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), CARGOS_OPERACAO);

        Veiculo veiculo = veiculoService.findByPlaca(dto.placa());
        if (!vinculoService.existsVinculo(veiculo.getPlaca(), dto.estacionamentoId())) {
            throw new ForbiddenException("Esse veiculo não está autorizado a entrar pois não possui vinculo com o estacionamento");
        }

        Registro ultimoRegistro = registroRepository.findTopByVeiculoAndEstacionamentoOrderByDataRegistroDesc(veiculo, estacionamento);

        if (ultimoRegistro != null && ultimoRegistro.getTipoRegistro() == TipoRegistro.ENTRADA)
            return registrarSaida(veiculo, estacionamento);
        else
            return registrarEntrada(veiculo, estacionamento);
    }

    /* CONSULTAS */

    @Transactional(readOnly = true)
    public Registro findEntityById(UUID id) {
        return registroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
    }

    @Transactional(readOnly = true)
    public RegistroOutputDTO findById(UUID id) {
        return registroMapper.toDto(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<RegistroOutputDTO> findByUsuario(UUID usuarioId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        if (!autenticado.getId().equals(usuarioId)) {
            throw new ForbiddenException("Você só pode consultar seu próprio histórico");
        }
        return registroMapper.toDtoList(registroRepository.findByVeiculoUsuarioIdOrderByDataRegistroDesc(usuarioId));
    }

    @Transactional(readOnly = true)
    public List<RegistroOutputDTO> findByEstacionamento(UUID estacionamentoId) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(estacionamentoId);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), CARGOS_OPERACAO);

        return registroMapper.toDtoList(registroRepository.findByEstacionamentoIdOrderByDataRegistroDesc(estacionamentoId));
    }

    /* HELPERS */

    private UUID empresaIdOf(Estacionamento e) {
        if (e == null || e.getEmpresa() == null) return null;
        return e.getEmpresa().getId();
    }

    private RegistroOutputDTO registrarEntrada(Veiculo veiculo, Estacionamento estacionamento) {
        Registro newRegistro = new Registro(veiculo, estacionamento, TipoRegistro.ENTRADA);
        estacionamento.entrarVeiculo();
        return registroMapper.toDto(registroRepository.save(newRegistro));
    }

    private RegistroOutputDTO registrarSaida(Veiculo veiculo, Estacionamento estacionamento) {
        Registro newRegistro = new Registro(veiculo, estacionamento, TipoRegistro.SAIDA);
        estacionamento.sairVeiculo();
        return registroMapper.toDto(registroRepository.save(newRegistro));
    }
}
