package br.com.estacioneja.services.Empresa;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Acesso.TipoAcesso;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.services.Acesso.AcessoService;

@Service
public class CadastroRepresentanteService {
    private final AcessoService acessoService;

    public CadastroRepresentanteService(@Lazy AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    public void cadastrarRepresentante(Long representanteId, Long empresaId) {
        acessoService.create(new AcessoDTO(TipoAcesso.MASTER, representanteId, empresaId));
    }
}
