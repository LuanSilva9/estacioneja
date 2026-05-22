package br.com.estacioneja.modules.endereco;

import org.springframework.stereotype.Service;

import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.modules.endereco.dto.EnderecoDTO;
import br.com.estacioneja.modules.endereco.dto.EnderecoOutputDTO;
import br.com.estacioneja.modules.endereco.dto.EnderecoUpdateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    /* TRANSACOES */

    @Transactional
    public Endereco create(EnderecoDTO dto) {
        Endereco newEndereco = new Endereco(dto.logradouro(), dto.bairro(), dto.cidade(), dto.uf(), dto.cep(), dto.latitude(), dto.longitude());
        return enderecoRepository.save(newEndereco);
    }

    @Transactional
    public void update(Long id, EnderecoUpdateDto dto) {
        Endereco endereco = findEntityById(id);

        endereco.setLogradouro(dto.logradouro());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setUf(dto.uf());
        endereco.setCep(dto.cep());
        endereco.setLatitude(dto.latitude());
        endereco.setLongitude(dto.longitude());
    }

    @Transactional
    public void delete(Long id) {
        Endereco endereco = findEntityById(id);
        enderecoRepository.delete(endereco);
    }

    /* CONSULTAS */

    public Endereco findEntityById(Long id) {
        return enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }

    public EnderecoOutputDTO findById(Long id) {
        return enderecoMapper.toDto(findEntityById(id));
    }
}
