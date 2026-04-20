package br.com.estacioneja.services.Acesso;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Acesso.AcessoRepository;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.update.AcessoUpdateDto;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.AcessoMapper;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IAcesso;

@Service
@RequiredArgsConstructor
public class AcessoService implements IAcesso {
    private final AcessoRepository acessoRepository;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final AcessoMapper acessoMapper;

    /* TRANSACOES */

    @Override @Transactional
    public AcessoOutputDTO create(AcessoDTO dto, UUID empresaId)  {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());
        Empresa empresa = empresaService.findEntityById(empresaId);

        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, empresa);

        return acessoMapper.toDto(acessoRepository.save(newAcesso));
    }  

    @Override @Transactional
    public void update(UUID id, AcessoUpdateDto dto) {
        Acesso acesso = findEntityById(id);

        acesso.setTipoAcesso(dto.tipoAcesso());
        
        acessoRepository.save(acesso);
    }

    @Override @Transactional
    public void delete(UUID id) {
        acessoRepository.deleteById(id);
    }

    /* CONSULTAS */

    @Override @Transactional(readOnly = true)
    public Acesso findEntityById(UUID id) {
        return acessoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado"));
    }

    @Override @Transactional(readOnly = true)
    public AcessoOutputDTO findById(UUID id) {
        return acessoMapper.toDto(findEntityById(id));
    }

    @Override @Transactional(readOnly = true)
    public List<AcessoOutputDTO> findAccessByEmpresa(UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);

        return acessoMapper.toDtoList(this.acessoRepository.findAllByEmpresa(empresa));
    }

    @Override @Transactional(readOnly = true)
    public List<AcessoOutputDTO> findAccessByUser(Usuario usuario) {
        return acessoMapper.toDtoList(this.acessoRepository.findAllByUsuario(usuario));
    }

 
    @Override @Transactional(readOnly = true)
    public AcessoOutputDTO findAccessByUserAndEmpresaId(Usuario usuario, UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);

        return  acessoMapper.toDto(acessoRepository.findByUsuarioAndEmpresa(usuario, empresa));
    }

    @Override @Transactional(readOnly = true)
    public List<Usuario> findAllUsersByEmpresa(UUID empresaId) {
        return this.acessoRepository.findAllUsersByEmpresa(empresaId);
    }

}
