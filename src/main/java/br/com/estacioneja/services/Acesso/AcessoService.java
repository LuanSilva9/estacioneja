package br.com.estacioneja.services.Acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Acesso.AcessoRepository;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.AcessoDTO;

@Service
public class AcessoService {
    @Autowired
    private AcessoRepository acessoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    public Acesso createAccess(AcessoDTO dto) throws Exception {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new Exception("Usuario não encontrado"));
        Empresa empresa = empresaRepository.findById(dto.empresaId()).orElseThrow(() -> new Exception("Empresa não encontrada"));

        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, empresa);

        return acessoRepository.save(newAcesso);
    }  
}
