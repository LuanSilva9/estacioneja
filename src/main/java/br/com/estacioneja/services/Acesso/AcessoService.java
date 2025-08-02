package br.com.estacioneja.services.Acesso;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Acesso.TipoAcesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Acesso.AcessoRepository;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.exceptions.custom.AccessNotFoundException;
import br.com.estacioneja.exceptions.custom.CompanyNotFoundException;
import br.com.estacioneja.exceptions.custom.UserNotFoundException;
import br.com.estacioneja.usecases.interfaces.IAcesso;
import jakarta.transaction.Transactional;

@Service
public class AcessoService implements IAcesso {
    private final AcessoRepository acessoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    public AcessoService(AcessoRepository acessoRepository, UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository) {
        this.acessoRepository = acessoRepository;
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public Acesso createAccess(AcessoDTO dto)  {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(UserNotFoundException::new);
        Empresa empresa = empresaRepository.findById(dto.empresaId()).orElseThrow(CompanyNotFoundException::new);

        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, empresa);

        return acessoRepository.save(newAcesso);
    }  

    @Transactional
    public Acesso putAccess(Long id, TipoAcesso tipoAcesso) throws Exception {
        Acesso acesso = getById(id);

        acesso.setTipoAcesso(tipoAcesso);

        return acessoRepository.save(acesso);
    }

    @Transactional
    public Acesso deleteAccess(Long id) throws Exception {
        Acesso acesso = getById(id);
        
        acessoRepository.deleteById(id);

        return acesso;
    }

    @Override
    public Acesso getById(Long id) {
        return acessoRepository.findById(id).orElseThrow(AccessNotFoundException::new);
    }
}
