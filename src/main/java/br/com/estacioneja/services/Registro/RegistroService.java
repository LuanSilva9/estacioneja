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
import jakarta.transaction.Transactional;

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

    @Override @Transactional
    public RegistroOutputDTO create(RegistroDTO dto) {
        Veiculo veiculo = veiculoService.findByPlaca(dto.placa());
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());
        
        if(!vinculoService.existsByEstacionamentoAndVeiculo(veiculo, estacionamento)) throw new ForbiddenException("Esse veiculo não está autorizado a entrar pois não possui vinculo com o estacionamento");

        Registro entradaExistente = registroRepository.findByVeiculoAndEstacionamentoAndTipoRegistro(veiculo, estacionamento, TipoRegistro.ENTRADA);
        
        if(entradaExistente != null) {
            return registrarSaida(entradaExistente);
        } else {
            Registro newRegistro = new Registro(veiculo, estacionamento);

            estacionamentoService.atualizarCapacidadeDisponivel(estacionamento.getId(), estacionamento.getCapacidadeDisponivel() - 1);

            return registroMapper.toDto(registroRepository.save(newRegistro));
        }
    }

    @Override @Transactional
    public void update(UUID id, RegistroDTO dto) {
        throw new UnsupportedOperationException("Unimplemented method cvupdate");
    }

    @Override @Transactional
    public void delete(UUID id) {
        Registro registro = findEntityById(id);

        registroRepository.delete(registro);
    }
    
    @Override @Transactional
    public RegistroOutputDTO registrarSaida(Registro registro) {
        registro.setTipoRegistro(TipoRegistro.SAIDA);
        estacionamentoService.atualizarCapacidadeDisponivel(registro.getEstacionamento().getId(), registro.getEstacionamento().getCapacidadeDisponivel() + 1);

        return registroMapper.toDto(registroRepository.save(registro));
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
