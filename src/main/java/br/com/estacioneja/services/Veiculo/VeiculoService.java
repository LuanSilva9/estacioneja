package br.com.estacioneja.services.Veiculo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.repository.Veiculo.VeiculoRepository;
import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.exceptions.custom.ForbiddenException;
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
    public VeiculoOutputDTO create(VeiculoDTO dto, Usuario proprietario) {
        Veiculo newVeiculo = new Veiculo(dto, proprietario);

        return veiculoMapper.toDto(veiculoRepository.save(newVeiculo));
    }

    @Override @Transactional
    public void update(UUID id, VeiculoDTO dto, Usuario proprietario) {
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

        authorizeUser(veiculo, proprietario);

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
    public List<Veiculo> findByProprietarioId(UUID proprietarioId) {
        Usuario proprietario = usuarioService.findEntityById(proprietarioId);
        return veiculoRepository.findByUsuario(proprietario);
    }

    /* Authorize */
    @Override 
    public void authorizeUser(Veiculo veiculo, Usuario proprietario) {
        if(!veiculo.getUsuario().getId().equals(proprietario.getId())) throw new ForbiddenException("Você não possui permissão para executar essa funcionalidade");
    }

}
