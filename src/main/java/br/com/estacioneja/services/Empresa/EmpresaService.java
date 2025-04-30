package br.com.estacioneja.services.Empresa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.EmpresaDTO;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Empresa createCompany(EmpresaDTO dto) throws Exception{
        Usuario representante = usuarioRepository.findById(dto.representanteId()).orElseThrow(() -> new Exception("ID / Representante não encontrado!"));

        Empresa newEmpresa = new Empresa(dto, representante);

        return empresaRepository.save(newEmpresa);
    }

    public List<Empresa> listCompany() {
        return empresaRepository.findAll();
    }
}
