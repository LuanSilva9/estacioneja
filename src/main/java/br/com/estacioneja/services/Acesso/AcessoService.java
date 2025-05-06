package br.com.estacioneja.services.Acesso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Acesso.TipoAcesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Acesso.AcessoRepository;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.AcessoDTO;
import jakarta.transaction.Transactional;

@Service
public class AcessoService {
    @Autowired
    private AcessoRepository acessoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public Acesso createAccess(AcessoDTO dto) throws Exception {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new Exception("Usuario não encontrado"));
        Empresa empresa = empresaRepository.findById(dto.empresaId()).orElseThrow(() -> new Exception("Empresa não encontrada"));

        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, empresa);

        return acessoRepository.save(newAcesso);
    }  

    @Transactional
    public List<Acesso> listAccess() {
        return acessoRepository.findAll();
    }

    @Transactional
    public Acesso listAccessById(Long id) throws Exception {
        return acessoRepository.findById(id).orElseThrow(() -> new Exception("Nenhum ID Encontrado"));
    }

    @Transactional
    public Acesso putAccess(Long id, TipoAcesso tipoAcesso) throws Exception {
        Acesso acesso = acessoRepository.findById(id).orElseThrow(() -> new Exception("Acesso não encontrado"));

        acesso.setTipoAcesso(tipoAcesso);

        return acessoRepository.save(acesso);
    }

    @Transactional
    public Acesso deleteAccess(Long id) throws Exception {
        Acesso acesso = acessoRepository.findById(id).orElseThrow(() -> new Exception("Acesso não encontrado"));
        
        acessoRepository.deleteById(id);

        return acesso;
    }
}
