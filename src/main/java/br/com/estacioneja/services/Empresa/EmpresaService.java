package br.com.estacioneja.services.Empresa;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.dto.update.EmpresaUpdateDto;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.EmpresaMapper;
import br.com.estacioneja.services.Endereco.EnderecoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IEmpresa;


@Service
@RequiredArgsConstructor
public class EmpresaService implements IEmpresa {
    private final EmpresaRepository empresaRepository;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private final EmpresaMapper empresaMapper;

    /* TRANSACOES */
    
    @Override @Transactional
    public EmpresaOutputDTO create(EmpresaDTO dto) {
        Usuario representante = usuarioService.findEntityById(dto.representanteId());
        Endereco endereco = enderecoService.create(dto.endereco());

        Empresa empresaPai = null;
    
        if (dto.empresaId() != null) {
            empresaPai = findEntityById(dto.empresaId());
        }

        Empresa newEmpresa;

        if (empresaPai != null) {
            newEmpresa = Empresa.criarFilial(
                dto.nome(), endereco, dto.tipoEmpresa(),
                dto.cnpj(), dto.prefixo(), dto.plano(),
                empresaPai, representante
            );
        } else {
            newEmpresa = Empresa.criarMatriz(
                representante, dto.nome(), endereco,
                dto.tipoEmpresa(), dto.cnpj(),
                dto.prefixo(), dto.plano()
            );
        }

        empresaRepository.save(newEmpresa);

        return empresaMapper.toDto(newEmpresa);
    }

    @Override @Transactional
    public void update(UUID id, EmpresaUpdateDto dto) {
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
    
    @Override @Transactional(readOnly = true)
    public Empresa findEntityById(UUID empresaId) {
        return empresaRepository.findById(empresaId).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada")); 
    }
    
    @Override @Transactional(readOnly = true)
    public EmpresaOutputDTO findById(UUID empresaId) {
       return empresaMapper.toDto(findEntityById(empresaId));
    }
    
    
    @Override @Transactional(readOnly = true)
    public List<EmpresaOutputDTO> findAll() {
        return empresaMapper.toDtoList(empresaRepository.findAll());
    }
}
