package br.com.estacioneja.services.Veiculo;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.repository.Veiculo.VeiculoRepository;
import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.dto.update.VeiculoUpdateDto;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.VeiculoMapper;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IVeiculo;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class VeiculoService implements IVeiculo {
    private final VeiculoRepository veiculoRepository;
    private final UsuarioService usuarioService;
    private final VeiculoMapper veiculoMapper;

    /* TRANSACOES */
    @Override @Transactional
    public VeiculoOutputDTO create(VeiculoDTO dto, Usuario proprietario) {
        Veiculo newVeiculo = new Veiculo(dto, proprietario);

        return veiculoMapper.toDto(veiculoRepository.save(newVeiculo));
    }

    @Override @Transactional
    public void update(UUID id, VeiculoUpdateDto dto, Usuario proprietario) {
        Veiculo veiculo = findEntityById(id, proprietario);

        veiculo.setCor(dto.cor());
        veiculo.setModelo(dto.modelo());
        veiculo.setTipoVeiculo(dto.tipoVeiculo());
    }

    @Override @Transactional
    public void delete(UUID id, Usuario proprietario) {
        Veiculo veiculo = findEntityById(id, proprietario);

        veiculoRepository.delete(veiculo);
    }

    /* CONSULTAS */
    @Override
    public Veiculo findEntityById(UUID id, Usuario proprietario) {
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        return veiculo;
    }

    @Override 
    public Veiculo findByPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    @Override
    public VeiculoOutputDTO findById(UUID id, Usuario proprietario) {
        return veiculoMapper.toDto(findEntityById(id, proprietario));
    }

    @Override
    public List<VeiculoOutputDTO> findByProprietarioId(UUID proprietarioId) {
        Usuario proprietario = usuarioService.findEntityById(proprietarioId);
        return veiculoMapper.toDtoList(veiculoRepository.findByUsuario(proprietario));
    }

}
