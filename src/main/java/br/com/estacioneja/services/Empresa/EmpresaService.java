package br.com.estacioneja.services.Empresa;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.events.EmpresaCriada.EmpresaCriadaEvent;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.exceptions.custom.CompanyNotFoundException;
import br.com.estacioneja.exceptions.custom.DuplicateCompanyException;
import br.com.estacioneja.infra.config.mapper.EmpresaMapper;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IEmpresa;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService implements IEmpresa {
    private final EmpresaRepository empresaRepository;
    private final UsuarioService usuarioService;
    private final ApplicationEventPublisher eventPublisher;
    private final EmpresaMapper empresaMapper;

    public EmpresaService(EmpresaRepository empresaRepository, UsuarioService usuarioService, ApplicationEventPublisher eventPublisher, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.usuarioService = usuarioService;
        this.eventPublisher = eventPublisher;
        this.empresaMapper = empresaMapper;
    }

    
    /* TRANSACOES */
    
    @Override @Transactional 
    public EmpresaOutputDTO create(EmpresaDTO dto) {
        existsByCnpj(dto.cnpj());
        
        Usuario representante = usuarioService.findEntityById(dto.representanteId());
        
        Empresa newEmpresa = new Empresa(dto, representante);
        
        empresaRepository.save(newEmpresa);

        eventPublisher.publishEvent(new EmpresaCriadaEvent(newEmpresa.getId(), representante.getId()));
        
        return empresaMapper.toDto(newEmpresa);
    }

    @Override @Transactional
    public EmpresaOutputDTO update(Long id, EmpresaDTO dto) {
        existsByCnpj(dto.cnpj());
        Empresa empresa = findEntityById(id);
        Usuario representante = usuarioService.findEntityById(id);
        
        empresa.setRepresentante(representante);
        empresa.setNome(dto.nome());
        empresa.setPrefixo(dto.prefixo());
        empresa.setCnpj(dto.cnpj());
        empresa.setEndereco(dto.endereco());
        empresa.setTipoEmpresa(dto.tipoEmpresa());
        
        return empresaMapper.toDto(empresaRepository.save(empresa));
    }
    
    @Override @Transactional
    public void delete(Long id) {
        Empresa empresa = findEntityById(id);
        
        empresaRepository.delete(empresa);
    }

    @Override
    public void existsByCnpj(String cnpj) {
        if(this.empresaRepository.existsByCnpj(cnpj)) throw new DuplicateCompanyException();
    } 
    
    /* CONSULTAS */
    
    @Override
    public Empresa findEntityById(Long empresaId) {
        return empresaRepository.findById(empresaId).orElseThrow(CompanyNotFoundException::new); 
    }
    
    @Override
    public EmpresaOutputDTO findById(Long empresaId) {
       return empresaMapper.toDto(findEntityById(empresaId));
    }
    
    
    @Override
    public List<EmpresaOutputDTO> findAll() {
        return empresaMapper.toDtoList(empresaRepository.findAll());
    }
}
