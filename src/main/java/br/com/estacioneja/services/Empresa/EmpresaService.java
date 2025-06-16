package br.com.estacioneja.services.Empresa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.i.EmpresaDTO;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Empresa createCompany(EmpresaDTO dto) throws Exception {
        Usuario representante = usuarioRepository.findById(dto.representanteId()).orElseThrow(() -> new Exception("ID / Representante não encontrado!"));

        Empresa newEmpresa = new Empresa(dto, representante);

        return empresaRepository.save(newEmpresa);
    }

    @Transactional
    public List<Empresa> listCompany() {
        return empresaRepository.findAll();
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
        
        // List<Estacionamento> estacionamentosVinculados = estacionamentoService.listEstacionamentosByCompany(empresa.getId());

        // for(int i = 0; i < estacionamentosVinculados.size(); i++) {
        //     estacionamentoService.deleteEstacionamento(estacionamentosVinculados.get(i).getId());
        // }

        empresaRepository.delete(empresa);

        return empresa;
    }
}
