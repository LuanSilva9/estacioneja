package br.com.estacioneja.services.Empresa;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Acesso.TipoAcesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.exceptions.custom.CompanyNotFoundException;
import br.com.estacioneja.infra.config.mapper.EmpresaMapper;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IEmpresa;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService implements IEmpresa {
    private final EmpresaRepository empresaRepository;
    private final UsuarioService usuarioService;
    private final AcessoService acessoService;
    private final EmpresaMapper empresaMapper;

    public EmpresaService(EmpresaRepository empresaRepository, UsuarioService usuarioService, AcessoService acessoService, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.usuarioService = usuarioService;
        this.acessoService = acessoService;
        this.empresaMapper = empresaMapper;
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

    /* TRANSACOES */

    @Override @Transactional
    public EmpresaOutputDTO create(EmpresaDTO dto) {
        Usuario representante = usuarioService.findEntityById(dto.representanteId());
        
        Empresa newEmpresa = new Empresa(dto, representante);

        empresaRepository.save(newEmpresa);
        
        acessoService.createAccess(new AcessoDTO(TipoAcesso.MASTER, representante.getId(), newEmpresa.getId()));

        return empresaMapper.toDto(newEmpresa);
    }

    @Override @Transactional
    public EmpresaOutputDTO update(Long id, EmpresaDTO dto) {
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

}
