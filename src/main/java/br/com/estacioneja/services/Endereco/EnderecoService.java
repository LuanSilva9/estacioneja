package br.com.estacioneja.services.Endereco;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.repository.Endereco.EnderecoRepository;
import br.com.estacioneja.dto.input.EnderecoDTO;
import br.com.estacioneja.dto.output.EnderecoOutputDTO;
import br.com.estacioneja.dto.update.EnderecoUpdateDto;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.EnderecoMapper;
import br.com.estacioneja.usecases.interfaces.IEndereco;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EnderecoService implements IEndereco {
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    /* TRANSACOES */

    @Override @Transactional
    public Endereco create(EnderecoDTO dto) {
        Endereco newEndereco = new Endereco(dto.logradouro(), dto.bairro(), dto.cidade(), dto.uf(), dto.cep(), dto.latitude(), dto.longitude());

        return enderecoRepository.save(newEndereco);
    }

    @Override @Transactional
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

    @Override @Transactional
    public void delete(Long id) {
        Endereco endereco = findEntityById(id);

        enderecoRepository.delete(endereco);
    }

    /* CONSULTAS */

    @Override
    public Endereco findEntityById(Long id) {
        return enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }

    @Override
    public EnderecoOutputDTO findById(Long id) {
        return enderecoMapper.toDto(findEntityById(id));
    }
}
