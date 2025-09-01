package br.com.estacioneja.services.Vinculo;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Estacionamento.StatusEstacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.repository.Vinculo.VinculoRepository;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.exceptions.custom.ParkIsPrivateException;
import br.com.estacioneja.exceptions.custom.VincleNotFoundException;
import br.com.estacioneja.infra.config.mapper.VinculoMapper;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IVinculo;
import jakarta.transaction.Transactional;

@Service
public class VinculoService implements IVinculo {
    private final VinculoRepository vinculoRepository;
    private final UsuarioService usuarioService;
    private final EstacionamentoService estacionamentoService;
    private final VinculoMapper vinculoMapper;

    public VinculoService(VinculoRepository vinculoRepository, UsuarioService usuarioService, EstacionamentoService estacionamentoService,VinculoMapper vinculoMapper) {
        this.vinculoRepository = vinculoRepository;
        this.usuarioService = usuarioService;
        this.estacionamentoService = estacionamentoService;
        this.vinculoMapper = vinculoMapper;
    }

    @Override
    public List<VinculoOutputDTO> findVincleByUserId(Long id) {
        Usuario usuario = usuarioService.findEntityById(id);

        return vinculoMapper.toDtoList(vinculoRepository.findAllByUsuario(usuario));
    }


    @Override @Transactional
    public VinculoOutputDTO create(VinculoDTO dto) {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());

        if(estacionamento.getStatusEstacionamento().equals(StatusEstacionamento.PRIVADO)) throw new ParkIsPrivateException();

        Vinculo vinculo = new Vinculo(usuario, estacionamento);
        
        return vinculoMapper.toDto(vinculoRepository.save(vinculo));
    }

    @Override @Transactional
    public VinculoOutputDTO update(Long id, VinculoDTO dto) {
        Vinculo vinculo = findEntityById(id);

        vinculo.setEstacionamento(estacionamentoService.findEntityById(dto.estacionamentoId()));
        vinculo.setUsuario(usuarioService.findEntityById(dto.usuarioId()));

        return vinculoMapper.toDto(vinculoRepository.save(vinculo));
    }
    
    @Override @Transactional
    public void delete(Long id) {
        Vinculo vinculo = findEntityById(id);

        vinculoRepository.delete(vinculo);
    }

    @Override
    public Vinculo findEntityById(Long id) {
        return vinculoRepository.findById(id).orElseThrow(VincleNotFoundException::new);
    }

    @Override
    public VinculoOutputDTO findById(Long id) {
        return vinculoMapper.toDto(findEntityById(id));
    }


    // @Override
    // public Vinculo findByUsuarioAndEstacionamento(Usuario usuario, Estacionamento estacionamento) {
    //     return vinculoRepository.findByUsuarioAndEstacionamento(usuario, estacionamento).orElseThrow(VincleNotFoundException::new);
    // }

}
