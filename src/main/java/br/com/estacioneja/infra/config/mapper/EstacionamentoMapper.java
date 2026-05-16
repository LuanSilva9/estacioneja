package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
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
                empresaMapper.toDto(estacionamento.getEmpresa())
        );
    }
}
