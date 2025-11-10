package br.com.estacioneja.services.Registro;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.enums.TipoRegistro;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.repository.Registro.RegistroRepository;
import br.com.estacioneja.dto.input.RegistroDTO;
import br.com.estacioneja.dto.output.RegistroOutputDTO;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.exceptions.custom.ForbiddenException;
import br.com.estacioneja.infra.config.mapper.RegistroMapper;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.services.Veiculo.VeiculoService;
import br.com.estacioneja.services.Vinculo.VinculoService;
import br.com.estacioneja.usecases.interfaces.IRegistro;

@Service
public class RegistroService implements IRegistro {
    private final RegistroRepository registroRepository;
    private final RegistroMapper registroMapper;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;
    private final VinculoService vinculoService;

    public RegistroService(RegistroRepository registroRepository, RegistroMapper registroMapper, EstacionamentoService estacionamentoService, VeiculoService veiculoService, VinculoService vinculoService) {
        this.registroRepository = registroRepository;
        this.registroMapper = registroMapper;
        this.estacionamentoService = estacionamentoService;
        this.veiculoService = veiculoService;
        this.vinculoService = vinculoService;
    }

    /* TRANSACOES */

    @Override
    public RegistroOutputDTO create(RegistroDTO dto) {
        if(!vinculoService.existsByEstacionamentoAndProprietarioVeiculo(dto.placa(), dto.estacionamentoId())) throw new ForbiddenException("Esse veiculo não está autorizado a: " + dto.tipoRegistro() + " pois não possui vinculo com o estacionamento");

        Veiculo veiculo = veiculoService.findByPlaca(dto.placa());
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());

        Registro newRegistro = new Registro(veiculo, estacionamento, dto.tipoRegistro());

        if(dto.tipoRegistro() == TipoRegistro.ENTRADA) {
            estacionamentoService.atualizarCapacidadeDisponivel(estacionamento.getId(), estacionamento.getCapacidadeDisponivel() - 1);
        } else if (dto.tipoRegistro() == TipoRegistro.SAIDA) {
            estacionamentoService.atualizarCapacidadeDisponivel(estacionamento.getId(), estacionamento.getCapacidadeDisponivel() + 1);
        }

        return registroMapper.toDto(registroRepository.save(newRegistro));
    }

    @Override
    public void update(UUID id, RegistroDTO dto) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /* CONSULTAS */

    @Override
    public Registro findEntityById(UUID id) {
        return registroRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Registro não encontrado"));
    }

    @Override
    public RegistroOutputDTO findById(UUID id) {
        return registroMapper.toDto(findEntityById(id));
    }


}
