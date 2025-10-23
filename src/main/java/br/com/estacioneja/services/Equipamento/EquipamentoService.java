package br.com.estacioneja.services.Equipamento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.domain.model.Equipamento.Equipamento;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.repository.Equipamento.EquipamentoRepository;
import br.com.estacioneja.dto.input.EquipamentoDTO;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;
import br.com.estacioneja.dto.output.EquipamentoOutputDTO;
import br.com.estacioneja.exceptions.custom.EquipamentNotFoundException;
import br.com.estacioneja.infra.config.mapper.EquipamentoMapper;
import br.com.estacioneja.services.Conexao.ConexaoService;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.usecases.interfaces.IEquipamento;

@Service
public class EquipamentoService implements IEquipamento {
    private final EquipamentoRepository equipamentoRepository;
    private final ConexaoService conexaoService;
    private final EstacionamentoService estacionamentoService;
    private final EquipamentoMapper equipamentoMapper;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, ConexaoService conexaoService, EstacionamentoService estacionamentoService, EquipamentoMapper equipamentoMapper) {
        this.equipamentoRepository = equipamentoRepository;
        this.conexaoService = conexaoService;
        this.estacionamentoService = estacionamentoService;
        this.equipamentoMapper = equipamentoMapper;
    }

    /* TRANSACOES */
    @Override
    public EquipamentoOutputDTO create(EquipamentoDTO dto) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());
        ConexaoOutputDTO conexaoCriada = conexaoService.create(dto.conexao());

        Conexao conexao = conexaoService.findEntityById(conexaoCriada.id());
        
        Equipamento equipamento = new Equipamento(dto, estacionamento, conexao);

        return equipamentoMapper.toDto(equipamentoRepository.save(equipamento));
    }
    @Override
    public EquipamentoOutputDTO update(UUID id, EquipamentoDTO dto) {
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

        return equipamentoMapper.toDto(equipamentoRepository.save(equipamento));
    }
    @Override
    public void delete(UUID id) {
        Equipamento equipamento = findEntityById(id);

        conexaoService.delete(equipamento.getConexaoHardware().getId());
        equipamentoRepository.delete(equipamento);
    }

    /* CONSULTAS */
    @Override
    public Equipamento findEntityById(UUID id) {
        return equipamentoRepository.findById(id).orElseThrow(EquipamentNotFoundException::new);
    }
    @Override
    public EquipamentoOutputDTO findById(UUID id) {
        return equipamentoMapper.toDto(findEntityById(id));
    }
}
