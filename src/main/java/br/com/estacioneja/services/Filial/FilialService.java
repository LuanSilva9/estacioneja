package br.com.estacioneja.services.Filial;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.events.Empresa.FilialCriadaEvent;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Filial.FilialRepository;
import br.com.estacioneja.dto.input.FilialDTO;
import br.com.estacioneja.dto.output.FilialOutputDTO;
import br.com.estacioneja.dto.output.SolicitacaoOutputDTO;
import br.com.estacioneja.exceptions.custom.DuplicateCompanyException;
import br.com.estacioneja.exceptions.custom.FilialNotFoundException;
import br.com.estacioneja.infra.config.mapper.FilialMapper;
import br.com.estacioneja.infra.config.mapper.SolicitacaoMapper;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Endereco.EnderecoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IFilial;
import jakarta.transaction.Transactional;

@Service
public class FilialService implements IFilial {
    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final ApplicationEventPublisher eventPublisher;
    private final FilialRepository filialRepository;
    private final FilialMapper filialMapper;
    private final SolicitacaoMapper solicitacaoMapper;

    public FilialService(FilialRepository filialRepository, FilialMapper filialMapper, EnderecoService enderecoService, EmpresaService empresaService, ApplicationEventPublisher eventPublisher, UsuarioService usuarioService, SolicitacaoMapper solicitacaoMapper) {
        this.enderecoService = enderecoService;
        this.empresaService = empresaService;
        this.filialRepository = filialRepository;
        this.filialMapper = filialMapper;
        this.eventPublisher = eventPublisher;
        this.usuarioService = usuarioService;
        this.solicitacaoMapper = solicitacaoMapper;
    }


    /* TRANSACOES */
    @Override @Transactional
    public FilialOutputDTO create(FilialDTO dto) {
        existsByCnpj(dto.cnpj());

        Endereco endereco = enderecoService.toEntity(enderecoService.create(dto.endereco()));
        Usuario representante = usuarioService.findEntityById(dto.representanteId());
        Empresa empresa = empresaService.findEntityById(dto.empresaId());

        Filial newFilial = new Filial(dto, endereco, representante, empresa);

        Filial filialSaved = filialRepository.save(newFilial);

        eventPublisher.publishEvent(new FilialCriadaEvent(filialSaved.getId(), representante.getId()));

        return filialMapper.toDto(filialSaved);
    }

    @Override @Transactional
    public void update(UUID id, FilialDTO dto) {
        Endereco enderecoExistente = findEntityById(id).getEndereco();
        Endereco endereco = enderecoService.toEntity(enderecoService.updateAndReturn(enderecoExistente.getId(), dto.endereco()));

        Filial filial = findEntityById(id);

        filial.setNome(dto.nome());
        filial.setCnpj(dto.cnpj());
        filial.setPrefixo(dto.prefixo());
        filial.setPlano(dto.plano());
        filial.setEndereco(endereco);

        filialRepository.save(filial);
    }

    @Override @Transactional
    public void delete(UUID id) {
        Filial filial = findEntityById(id);

        filialRepository.delete(filial);
    }

    @Override
    public void existsByCnpj(String cnpj) {
        if(this.filialRepository.existsByCnpj(cnpj)) throw new DuplicateCompanyException();
    } 

    /* CONSULTAS */

    @Override
    public Filial findEntityById(UUID id) {
        return filialRepository.findById(id).orElseThrow(FilialNotFoundException::new);
    }

    @Override
    public FilialOutputDTO findById(UUID id) {
        return filialMapper.toDto(findEntityById(id));
    }


    @Override
    public List<FilialOutputDTO> findAllByEmpresaId(Long empresaId) {
        return filialMapper.toDtoList(filialRepository.findAllByEmpresaId(empresaId));
    }

    @Override
    public List<SolicitacaoOutputDTO> findAllRequests(UUID id) {
        return this.solicitacaoMapper.toDtoList(filialRepository.findSolicitacoesByFilial(id));
    }
    
}
