package br.com.estacioneja.services.Empresa;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.events.Empresa.EmpresaCriadaEvent;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.EmpresaMapper;
import br.com.estacioneja.services.Endereco.EnderecoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IEmpresa;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService implements IEmpresa {
    private final EmpresaRepository empresaRepository;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private final ApplicationEventPublisher eventPublisher;
    private final EmpresaMapper empresaMapper;

    public EmpresaService(EmpresaRepository empresaRepository, UsuarioService usuarioService, EnderecoService enderecoService, EmpresaMapper empresaMapper, ApplicationEventPublisher eventPublisher) {
        this.empresaRepository = empresaRepository;
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.eventPublisher = eventPublisher;
        this.empresaMapper = empresaMapper;
    }

    
    /* TRANSACOES */
    
    @Override @Transactional 
    public EmpresaOutputDTO create(EmpresaDTO dto) {
        Usuario representante = usuarioService.findEntityById(dto.representanteId());
        Endereco endereco = enderecoService.toEntity(enderecoService.create(dto.endereco()));

        Empresa newEmpresa;

        if(dto.empresaId() == null) {
            /* EMPRESA */
            newEmpresa = new Empresa(dto, representante, endereco, null);
        } else {
            /* FILIAL */
            Empresa empresaPai = findEntityById(dto.empresaId());

            newEmpresa = new Empresa(dto, representante, endereco, empresaPai);
        }
        
        empresaRepository.save(newEmpresa);
        eventPublisher.publishEvent(new EmpresaCriadaEvent(newEmpresa.getId(), representante.getId()));

        return empresaMapper.toDto(newEmpresa);
    }

    @Override @Transactional
    public void update(UUID id, EmpresaDTO dto) {
        Empresa empresa = findEntityById(id);

        empresa.setNome(dto.nome());
        empresa.setTipoEmpresa(dto.tipoEmpresa());        
    }
    
    @Override @Transactional
    public void delete(UUID id) {
        Empresa empresa = findEntityById(id);
        
        empresaRepository.delete(empresa);
    }
    
    /* CONSULTAS */
    
    @Override
    public Empresa findEntityById(UUID empresaId) {
        return empresaRepository.findById(empresaId).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada")); 
    }
    
    @Override
    public EmpresaOutputDTO findById(UUID empresaId) {
       return empresaMapper.toDto(findEntityById(empresaId));
    }
    
    
    @Override
    public List<EmpresaOutputDTO> findAll() {
        return empresaMapper.toDtoList(empresaRepository.findAll());
    }
}
