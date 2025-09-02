package br.com.estacioneja.services.Empresa;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Acesso.TipoAcesso;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Vinculo.VinculoService;

@Service
public class CadastroRepresentanteService {
    private final AcessoService acessoService;
    private final VinculoService vinculoService;

    public CadastroRepresentanteService(@Lazy AcessoService acessoService, @Lazy VinculoService vinculoService) {
        this.acessoService = acessoService;
        this.vinculoService = vinculoService;
    }

    public void cadastrarRepresentante(Long representanteId, Long empresaId) {
        acessoService.create(new AcessoDTO(TipoAcesso.MASTER, representanteId, empresaId));
    }
}
