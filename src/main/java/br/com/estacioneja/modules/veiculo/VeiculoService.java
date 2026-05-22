package br.com.estacioneja.modules.veiculo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.usuario.UsuarioService;
import br.com.estacioneja.modules.veiculo.dto.VeiculoDTO;
import br.com.estacioneja.modules.veiculo.dto.VeiculoOutputDTO;
import br.com.estacioneja.modules.veiculo.dto.VeiculoUpdateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository veiculoRepository;
    private final UsuarioService usuarioService;
    private final VeiculoMapper veiculoMapper;

    /* TRANSACOES */

    @Transactional
    public VeiculoOutputDTO create(VeiculoDTO dto, Usuario proprietario) {
        Veiculo newVeiculo = new Veiculo(dto, proprietario);
        return veiculoMapper.toDto(veiculoRepository.save(newVeiculo));
    }

    @Transactional
    public void update(UUID id, VeiculoUpdateDto dto, Usuario proprietario) {
        Veiculo veiculo = findEntityById(id, proprietario);

        veiculo.setCor(dto.cor());
        veiculo.setModelo(dto.modelo());
        veiculo.setTipoVeiculo(dto.tipoVeiculo());
    }

    @Transactional
    public void delete(UUID id, Usuario proprietario) {
        Veiculo veiculo = findEntityById(id, proprietario);
        veiculoRepository.delete(veiculo);
    }

    /* CONSULTAS */

    public Veiculo findEntityById(UUID id, Usuario proprietario) {
        return veiculoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    public Veiculo findByPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    public VeiculoOutputDTO findById(UUID id, Usuario proprietario) {
        return veiculoMapper.toDto(findEntityById(id, proprietario));
    }

    public List<VeiculoOutputDTO> findByProprietarioId(UUID proprietarioId) {
        Usuario proprietario = usuarioService.findEntityById(proprietarioId);
        return veiculoMapper.toDtoList(veiculoRepository.findByUsuario(proprietario));
    }
}
