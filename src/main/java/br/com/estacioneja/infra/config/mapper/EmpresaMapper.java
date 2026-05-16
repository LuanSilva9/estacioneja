package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmpresaMapper extends AbstractMapper<Empresa, EmpresaOutputDTO> {

    private final UsuarioMapper usuarioMapper;
    private final EnderecoMapper enderecoMapper;

    @Override
    public EmpresaOutputDTO toDto(Empresa empresa) {
        if (empresa == null) return null;
        return new EmpresaOutputDTO(
                empresa.getId(),
                empresa.getNome(),
                empresa.getTipoEmpresa(),
                usuarioMapper.toDto(empresa.getRepresentante()),
                empresa.getCnpj(),
                empresa.getPrefixo(),
                empresa.getPlano(),
                enderecoMapper.toDto(empresa.getEndereco())
        );
    }
}
