package br.com.estacioneja.services.Empresa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.domain.model.Acesso.Actor;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaOrquestradorService {

    private final EmpresaService empresaService;
    private final AcessoService acessoService;

    @Transactional
    public EmpresaOutputDTO create(EmpresaDTO dto) {

        EmpresaOutputDTO empresa = empresaService.create(dto);

        acessoService.create(
            Actor.sistema(),
            new AcessoDTO(TipoAcesso.MASTER, dto.representanteId()),
            empresa.id()
        );

        return empresa;
    }
}
