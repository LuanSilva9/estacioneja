package br.com.estacioneja.services.Empresa;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.events.Empresa.EmpresaCriadaEvent;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.exceptions.custom.CompanyNotFoundException;
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

    public EmpresaService(EmpresaRepository empresaRepository, UsuarioService usuarioService, EmpresaMapper empresaMapper, ApplicationEventPublisher eventPublisher) {
        this.empresaRepository = empresaRepository;
        this.usuarioService = usuarioService;
        this.eventPublisher = eventPublisher;
        this.empresaMapper = empresaMapper;
    }

    
    /* TRANSACOES */
    
    @Override @Transactional 
    public EmpresaOutputDTO create(EmpresaDTO dto) {
        Usuario representanteMaster = usuarioService.findEntityById(dto.representanteId());

        Empresa newEmpresa = new Empresa(dto, representanteMaster);
        
        empresaRepository.save(newEmpresa);

        eventPublisher.publishEvent(new EmpresaCriadaEvent(newEmpresa.getId(), representanteMaster.getId()));
        
        return empresaMapper.toDto(newEmpresa);
    }

    @Override @Transactional
    public EmpresaOutputDTO update(Long id, EmpresaDTO dto) {
        Empresa empresa = findEntityById(id);
        Usuario representanteMaster = usuarioService.findEntityById(dto.representanteId());

        
        empresa.setNome(dto.nome());
        empresa.setTipoEmpresa(dto.tipoEmpresa());
        empresa.setRepresentanteMaster(representanteMaster);
        
        return empresaMapper.toDto(empresaRepository.save(empresa));
    }
    
    @Override @Transactional
    public void delete(Long id) {
        Empresa empresa = findEntityById(id);
        
        empresaRepository.delete(empresa);
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
