package br.com.estacioneja.modules.empresa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.modules.acessos.AcessoService;
import br.com.estacioneja.modules.acessos.Actor;
import br.com.estacioneja.modules.acessos.dto.AcessoDTO;
import br.com.estacioneja.modules.empresa.dto.EmpresaDTO;
import br.com.estacioneja.modules.empresa.dto.EmpresaOutputDTO;
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
