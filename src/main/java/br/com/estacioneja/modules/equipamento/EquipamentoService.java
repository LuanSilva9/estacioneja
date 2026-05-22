package br.com.estacioneja.modules.equipamento;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.errors.exceptions.DuplicateException;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.modules.conexao.Conexao;
import br.com.estacioneja.modules.conexao.ConexaoService;
import br.com.estacioneja.modules.conexao.dto.ConexaoOutputDTO;
import br.com.estacioneja.modules.equipamento.dto.EquipamentoDTO;
import br.com.estacioneja.modules.equipamento.dto.EquipamentoOutputDTO;
import br.com.estacioneja.modules.equipamento.dto.EquipamentoUpdateDto;
import br.com.estacioneja.modules.estacionamento.Estacionamento;
import br.com.estacioneja.modules.estacionamento.EstacionamentoService;
import br.com.estacioneja.modules.security.AuthorizationService;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.shared.enums.TipoEquipamento;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipamentoService {
    private final EquipamentoRepository equipamentoRepository;
    private final ConexaoService conexaoService;
    private final EstacionamentoService estacionamentoService;
    private final EquipamentoMapper equipamentoMapper;
    private final AuthorizationService authorizationService;

    private static final TipoAcesso[] CARGOS_GESTAO = { TipoAcesso.MASTER, TipoAcesso.CADASTRO_GESTAO };

    /* TRANSACOES */

    @Transactional
    public EquipamentoOutputDTO create(EquipamentoDTO dto) {
        verificarDuplicidade(dto.tipoEquipamento(), dto.estacionamentoId());

        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(estacionamento), CARGOS_GESTAO);

        ConexaoOutputDTO conexaoCriada = conexaoService.create(dto.conexao());
        Conexao conexao = conexaoService.findEntityById(conexaoCriada.id());

        Equipamento equipamento = new Equipamento(dto, estacionamento, conexao);
        return equipamentoMapper.toDto(equipamentoRepository.save(equipamento));
    }

    @Transactional
    public void update(UUID id, EquipamentoUpdateDto dto) {
        Equipamento equipamento = findEntityById(id);
        Estacionamento destino = estacionamentoService.findEntityById(dto.estacionamentoId());

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(equipamento.getEstacionamento()), CARGOS_GESTAO);
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(destino), CARGOS_GESTAO);

        Conexao conexao = conexaoService.findEntityById(equipamento.getConexaoHardware().getId());
        conexaoService.update(conexao.getId(), dto.conexao());

        equipamento.setNome(dto.nome());
        equipamento.setDescricao(dto.descricao());
        equipamento.setModelo(dto.modelo());
        equipamento.setTipoEquipamento(dto.tipoEquipamento());
        equipamento.setEstacionamento(destino);
        equipamento.setConexaoHardware(conexao);

        equipamentoRepository.save(equipamento);
    }

    @Transactional
    public void delete(UUID id) {
        Equipamento equipamento = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireAnyEmpresaRole(autenticado, empresaIdOf(equipamento.getEstacionamento()), CARGOS_GESTAO);

        conexaoService.delete(equipamento.getConexaoHardware().getId());
        equipamentoRepository.delete(equipamento);
    }

    /* CONSULTAS */

    @Transactional(readOnly = true)
    public Equipamento findEntityById(UUID id) {
        return equipamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
    }

    @Transactional(readOnly = true)
    public EquipamentoOutputDTO findById(UUID id) {
        Equipamento equipamento = findEntityById(id);

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaAccess(autenticado, empresaIdOf(equipamento.getEstacionamento()));

        return equipamentoMapper.toDto(equipamento);
    }

    @Transactional(readOnly = true)
    public List<EquipamentoOutputDTO> findByEmpresa(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaAccess(autenticado, empresaId);

        List<Equipamento> equipamentos = equipamentoRepository.findByEmpresa(empresaId);
        return equipamentoMapper.toDtoList(equipamentos);
    }

    /* VALIDACOES */

    private void verificarDuplicidade(TipoEquipamento tipoEquipamento, UUID estacionamentoId) {
        if (equipamentoRepository.countEquipamentosWhereTipoAndEstacionamento(tipoEquipamento, estacionamentoId) > 0) {
            throw new DuplicateException("Já existe um equipamento para essas configurações!");
        }
    }

    /* HELPERS */

    private UUID empresaIdOf(Estacionamento e) {
        if (e == null || e.getEmpresa() == null) return null;
        return e.getEmpresa().getId();
    }
}
