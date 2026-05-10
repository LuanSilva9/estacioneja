package br.com.estacioneja.services.Equipamento;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.domain.enums.TipoEquipamento;
import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.domain.model.Equipamento.Equipamento;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.repository.Equipamento.EquipamentoRepository;
import br.com.estacioneja.dto.input.EquipamentoDTO;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;
import br.com.estacioneja.dto.output.EquipamentoOutputDTO;
import br.com.estacioneja.dto.update.EquipamentoUpdateDto;
import br.com.estacioneja.exceptions.custom.DuplicateException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.EquipamentoMapper;
import br.com.estacioneja.services.Conexao.ConexaoService;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.usecases.interfaces.IEquipamento;

@Service
@RequiredArgsConstructor
public class EquipamentoService implements IEquipamento {
    private final EquipamentoRepository equipamentoRepository;
    private final ConexaoService conexaoService;
    private final EstacionamentoService estacionamentoService;
    private final EquipamentoMapper equipamentoMapper;

    /* TRANSACOES */
    @Override @Transactional
    public EquipamentoOutputDTO create(EquipamentoDTO dto) {
        verificarDuplicidade(dto.tipoEquipamento(), dto.estacionamentoId());

        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());

        ConexaoOutputDTO conexaoCriada = conexaoService.create(dto.conexao());

        Conexao conexao = conexaoService.findEntityById(conexaoCriada.id());
        
        Equipamento equipamento = new Equipamento(dto, estacionamento, conexao);

        return equipamentoMapper.toDto(equipamentoRepository.save(equipamento));
    }
    @Override @Transactional
    public void update(UUID id, EquipamentoUpdateDto dto) {
        Equipamento equipamento = findEntityById(id);
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());
        Conexao conexao = conexaoService.findEntityById(equipamento.getConexaoHardware().getId());

        conexaoService.update(conexao.getId(), dto.conexao());

        equipamento.setNome(dto.nome());
        equipamento.setDescricao(dto.descricao());
        equipamento.setModelo(dto.modelo());
        equipamento.setTipoEquipamento(dto.tipoEquipamento());
        equipamento.setEstacionamento(estacionamento);
        equipamento.setConexaoHardware(conexao);

        equipamentoRepository.save(equipamento);
    }
    @Override @Transactional
    public void delete(UUID id) {
        Equipamento equipamento = findEntityById(id);

        conexaoService.delete(equipamento.getConexaoHardware().getId());
        equipamentoRepository.delete(equipamento);
    }

    /* CONSULTAS */
    @Override @Transactional(readOnly = true)
    public Equipamento findEntityById(UUID id) {
        return equipamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
    }
    @Override @Transactional(readOnly = true)
    public EquipamentoOutputDTO findById(UUID id) {
        return equipamentoMapper.toDto(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<EquipamentoOutputDTO> findByEmpresa(UUID empresaId) {        
        List<Equipamento> equipamentos = equipamentoRepository.findByEmpresa(empresaId);
        
        return equipamentoMapper.toDtoList(equipamentos);
    }


    /* VALIDACOES */

    private void verificarDuplicidade(TipoEquipamento tipoEquipamento, UUID estacionamentoId) {        
        if(equipamentoRepository.countEquipamentosWhereTipoAndEstacionamento(tipoEquipamento, estacionamentoId) > 0) {
            throw new DuplicateException("Já existe um equipamento para essas configurações!");
        }
    }
}
