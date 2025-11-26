package br.com.estacioneja.services.Vinculo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.repository.Vinculo.VinculoRepository;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.exceptions.custom.DuplicateException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.VinculoMapper;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.services.Veiculo.VeiculoService;
import br.com.estacioneja.usecases.interfaces.IVinculo;
import jakarta.transaction.Transactional;

@Service
public class VinculoService implements IVinculo {
    private final VinculoRepository vinculoRepository;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;
    private final VinculoMapper vinculoMapper;

    public VinculoService(VinculoRepository vinculoRepository, UsuarioService usuarioService, EstacionamentoService estacionamentoService, VeiculoService veiculoService, VinculoMapper vinculoMapper) {
        this.vinculoRepository = vinculoRepository;
        this.estacionamentoService = estacionamentoService;
        this.vinculoMapper = vinculoMapper;
        this.veiculoService = veiculoService;
    }

    /* TRANSACOES */    
    
    @Override
    @Transactional
    public VinculoOutputDTO create(VinculoDTO dto) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());
        Veiculo veiculo = veiculoService.findByPlaca(dto.placaVeiculo());
        Usuario usuario = veiculo.getUsuario();
        
        if(existsByEstacionamentoAndVeiculo(veiculo, estacionamento)) {
            throw new DuplicateException("Esse veiculo já está vinculado nesse estacionamento");
        }

        Vinculo vinculo = new Vinculo(estacionamento, usuario, veiculo);
        return vinculoMapper.toDto(vinculoRepository.save(vinculo));
    }
    
    @Override @Transactional
    public void update(UUID id, VinculoDTO dto) {
        Vinculo vinculo = findEntityById(id);
        
        vinculo.setEstacionamento(estacionamentoService.findEntityById(dto.estacionamentoId()));
        
        vinculoRepository.save(vinculo);
    }
    
    @Override @Transactional
    public void delete(UUID id) {
        Vinculo vinculo = findEntityById(id);
        
        vinculoRepository.delete(vinculo);
    }

    /* CONSULTAS */
    
    @Override
    public Vinculo findEntityById(UUID id) {
        return vinculoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vinculo não encontrado"));
    }
    
    @Override
    public VinculoOutputDTO findById(UUID id) {
        return vinculoMapper.toDto(findEntityById(id));
    }
    
    @Override
    public List<VinculoOutputDTO> findVincleByUser(Usuario usuario) {
        return vinculoMapper.toDtoList(vinculoRepository.findAllByUsuario(usuario));
    }

    @Override
    public Boolean hasVincle(String placaVeiculo, UUID estacionamentoId) {
        Veiculo veiculo = veiculoService.findByPlaca(placaVeiculo);
        Estacionamento estacionamento = estacionamentoService.findEntityById(estacionamentoId);

        return existsByEstacionamentoAndVeiculo(veiculo, estacionamento);
    }
    
    @Override
    public Boolean existsByEstacionamentoAndVeiculo(Veiculo veiculo, Estacionamento estacionamento) {
        return this.vinculoRepository.existsByEstacionamentoAndVeiculo(estacionamento, veiculo);
    }

}
