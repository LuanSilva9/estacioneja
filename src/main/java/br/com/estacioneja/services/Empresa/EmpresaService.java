package br.com.estacioneja.services.Empresa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Acesso.TipoAcesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.i.AcessoDTO;
import br.com.estacioneja.dto.i.EmpresaDTO;
import br.com.estacioneja.dto.o.EmpresaOutputDTO;
import br.com.estacioneja.infra.config.mapper.EmpresaMapper;
import br.com.estacioneja.services.Acesso.AcessoService;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Transactional
    public Empresa createCompany(EmpresaDTO dto) throws Exception {
        Usuario representante = usuarioRepository.findById(dto.representanteId()).orElseThrow(() -> new Exception("ID / Representante não encontrado!"));

        Empresa newEmpresa = new Empresa(dto, representante);
        
        acessoService.createAccess(new AcessoDTO(TipoAcesso.MASTER, representante.getId(), newEmpresa.getId()));

        return empresaRepository.save(newEmpresa);
    }

    @Transactional
    public List<EmpresaOutputDTO> listCompany() {
        return empresaMapper.toDtoList(empresaRepository.findAll());
    }

    @Transactional
    public Empresa updateCompany(Long id, EmpresaDTO dto) throws Exception {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new Exception("Empresa não encontrada"));
        
        Usuario representante = usuarioRepository.findById(dto.representanteId()).orElseThrow(() -> new Exception("ID / Representante não encontrado"));

        empresa.setRepresentante(representante);
        empresa.setNome(dto.nome());
        empresa.setPrefixo(dto.prefixo());
        empresa.setCnpj(dto.cnpj());
        empresa.setEndereco(dto.endereco());
        empresa.setTipoEmpresa(dto.tipoEmpresa());

        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa deleteCompany(Long id) throws Exception {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new Exception("Empresa não encontrada!"));

        empresaRepository.delete(empresa);

        return empresa;
    }
}
