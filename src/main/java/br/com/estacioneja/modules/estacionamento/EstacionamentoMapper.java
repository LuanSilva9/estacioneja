package br.com.estacioneja.modules.estacionamento;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.empresa.EmpresaMapper;
import br.com.estacioneja.modules.estacionamento.dto.EstacionamentoOutputDTO;
import br.com.estacioneja.shared.mapper.AbstractMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstacionamentoMapper extends AbstractMapper<Estacionamento, EstacionamentoOutputDTO> {

    private final EmpresaMapper empresaMapper;

    @Override
    public EstacionamentoOutputDTO toDto(Estacionamento estacionamento) {
        if (estacionamento == null) return null;
        
        return new EstacionamentoOutputDTO(
                estacionamento.getId(),
                estacionamento.getDescricao(),
                estacionamento.getPrivacidade(),
                estacionamento.getCapacidade(),
                estacionamento.getCapacidadeDisponivel(),
                empresaMapper.toDto(estacionamento.getEmpresa()),
                estacionamento.getRegraEstacionamento()
        );
    }
}
