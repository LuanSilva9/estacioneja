package br.com.estacioneja.services.Registro;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@RequiredArgsConstructor
public class RegistroService implements IRegistro {
    private final RegistroRepository registroRepository;
    private final RegistroMapper registroMapper;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;
    private final VinculoService vinculoService;

    /* TRANSACOES */

    @Override @Transactional
    public RegistroOutputDTO create(RegistroDTO dto) {
        Veiculo veiculo = veiculoService.findByPlaca(dto.placa());
        
        if(!vinculoService.existsVinculo(veiculo.getPlaca(), dto.estacionamentoId())) throw new ForbiddenException("Esse veiculo não está autorizado a entrar pois não possui vinculo com o estacionamento");
        
        Estacionamento estacionamento = estacionamentoService.findEntityLocked(dto.estacionamentoId());
        
        Registro ultimoRegistro = registroRepository.findTopByVeiculoAndEstacionamentoOrderByDataRegistroDesc(veiculo, estacionamento);

        if (ultimoRegistro != null && ultimoRegistro.getTipoRegistro() == TipoRegistro.ENTRADA) 
            return registrarSaida(veiculo, estacionamento);
        else 
            return registrarEntrada(veiculo, estacionamento);
    }

    /* CONSULTAS */

    @Override @Transactional(readOnly = true)
    public Registro findEntityById(UUID id) {
        return registroRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Registro não encontrado"));
    }

    @Override @Transactional(readOnly = true)
    public RegistroOutputDTO findById(UUID id) {
        return registroMapper.toDto(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<RegistroOutputDTO> findByUsuario(UUID usuarioId) {
        return registroMapper.toDtoList(registroRepository.findByVeiculoUsuarioIdOrderByDataRegistroDesc(usuarioId));
    }

    @Transactional(readOnly = true)
    public List<RegistroOutputDTO> findByEstacionamento(UUID estacionamentoId) {
        return registroMapper.toDtoList(registroRepository.findByEstacionamentoIdOrderByDataRegistroDesc(estacionamentoId));
    }

    /* HELPERS */

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
