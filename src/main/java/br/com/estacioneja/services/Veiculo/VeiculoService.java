package br.com.estacioneja.services.Veiculo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.repository.Veiculo.VeiculoRepository;
import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.exceptions.custom.VeicleNotFoundException;
import br.com.estacioneja.infra.config.mapper.VeiculoMapper;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IVeiculo;
import jakarta.transaction.Transactional;

@Service
public class VeiculoService implements IVeiculo {
    private final VeiculoRepository veiculoRepository;
    private final UsuarioService usuarioService;
    private final VeiculoMapper veiculoMapper;
    
    public VeiculoService(VeiculoRepository veiculoRepository, UsuarioService usuarioService, VeiculoMapper veiculoMapper) {
        this.veiculoRepository = veiculoRepository;
        this.usuarioService = usuarioService;
        this.veiculoMapper = veiculoMapper;
    }

    /* TRANSACOES */
    @Override @Transactional
    public VeiculoOutputDTO create(VeiculoDTO dto) {
        Usuario proprietario = usuarioService.findEntityById(dto.proprietarioId());

        Veiculo newVeiculo = new Veiculo(dto, proprietario);

        return veiculoMapper.toDto(veiculoRepository.save(newVeiculo));
    }

    @Override
    public VeiculoOutputDTO update(UUID id, VeiculoDTO dto) {
        Veiculo veiculo = findEntityById(id);

        veiculo.setCor(dto.cor());
        veiculo.setModelo(dto.modelo());
        veiculo.setTipoVeiculo(dto.tipoVeiculo());

        return veiculoMapper.toDto(veiculoRepository.save(veiculo));
    }

    @Override
    public void delete(UUID id) {
        Veiculo veiculo = findEntityById(id);

        veiculoRepository.delete(veiculo);
    }

    /* CONSULTAS */
    @Override
    public Veiculo findEntityById(UUID id) {
        return veiculoRepository.findById(id).orElseThrow(VeicleNotFoundException::new);
    }

    @Override
    public VeiculoOutputDTO findById(UUID id) {
        return veiculoMapper.toDto(findEntityById(id));
    }

    @Override
    public List<Veiculo> findByProprietarioId(Long proprietarioId) {
        Usuario proprietario = usuarioService.findEntityById(proprietarioId);
        return veiculoRepository.findByProprietario(proprietario);
    }
}
