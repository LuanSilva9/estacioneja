package br.com.estacioneja.services.Endereco;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.repository.Endereco.EnderecoRepository;
import br.com.estacioneja.dto.input.EnderecoDTO;
import br.com.estacioneja.dto.output.EnderecoOutputDTO;
import br.com.estacioneja.exceptions.custom.AddressNotFoundException;
import br.com.estacioneja.infra.config.mapper.EnderecoMapper;
import br.com.estacioneja.usecases.interfaces.IEndereco;

@Service
public class EnderecoService implements IEndereco {
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    
    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /* TRANSACOES */

    public Endereco createEntity(EnderecoDTO dto) {
        Endereco newEndereco = new Endereco(dto);

        return enderecoRepository.save(newEndereco);
    }

    @Override
    public EnderecoOutputDTO create(EnderecoDTO dto) {
        Endereco newEndereco = new Endereco(dto);

        return enderecoMapper.toDto(enderecoRepository.save(newEndereco));
    }

    @Override
    public EnderecoOutputDTO update(Long id, EnderecoDTO dto) {
        Endereco endereco = findEntityById(id);

        endereco.setLogradouro(dto.logradouro());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setUf(dto.uf());
        endereco.setCep(dto.cep());
        endereco.setLatitude(dto.latitude());
        endereco.setLongitude(dto.longitude());

        return enderecoMapper.toDto(enderecoRepository.save(endereco));
    }

    @Override
    public void delete(Long id) {
        Endereco endereco = findEntityById(id);

        enderecoRepository.delete(endereco);
    }

    /* CONSULTAS */

    @Override
    public Endereco findEntityById(Long id) {
        return enderecoRepository.findById(id).orElseThrow(AddressNotFoundException::new);
    }

    @Override
    public EnderecoOutputDTO findById(Long id) {
        return enderecoMapper.toDto(findEntityById(id));
    }
    
}
