package br.com.estacioneja.services.Vinculo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.domain.repository.Vinculo.VinculoRepository;
import br.com.estacioneja.dto.VinculoDTO;
import jakarta.transaction.Transactional;

@Service
public class VinculoService {
    @Autowired private VinculoRepository vinculoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private EstacionamentoRepository estacionamentoRepository;


    @Transactional
    public List<Vinculo> listVinculos() {
        return vinculoRepository.findAll();
    }


    @Transactional
    public List<Vinculo> listVinculosByUserId(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuario não encontrado"));

        return vinculoRepository.findAllByUsuario(usuario);
    }


    @Transactional
    public Vinculo createVinculo(VinculoDTO dto) throws Exception {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new Exception("Usuario não encontrado"));
        Estacionamento estacionamento = estacionamentoRepository.findById(dto.estacionamentoId()).orElseThrow(() -> new Exception("Estacionamento não encontrado"));

        Vinculo vinculo = new Vinculo(usuario, estacionamento);
        
        return vinculoRepository.save(vinculo);
    }

    @Transactional
    public Vinculo deleteVinculo(VinculoDTO dto) throws Exception {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new Exception("Usuario não encontrado"));
        Estacionamento estacionamento = estacionamentoRepository.findById(dto.estacionamentoId()).orElseThrow(() -> new Exception("Estacionamento não encontrado"));

        Vinculo vinculo = vinculoRepository.findByUsuarioAndEstacionamento(usuario, estacionamento).orElseThrow(() -> new Exception("Ocorreu um erro!"));

        vinculoRepository.delete(vinculo);

        return vinculo;
    }
}
