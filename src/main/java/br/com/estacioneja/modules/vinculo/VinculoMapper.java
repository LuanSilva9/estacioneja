package br.com.estacioneja.modules.vinculo;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.empresa.Empresa;
import br.com.estacioneja.modules.endereco.Endereco;
import br.com.estacioneja.modules.estacionamento.Estacionamento;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.veiculo.Veiculo;
import br.com.estacioneja.modules.vinculo.dto.EstacionamentoCardUsuarioDTO;
import br.com.estacioneja.modules.vinculo.dto.EstacionamentoRotuloDTO;
import br.com.estacioneja.modules.vinculo.dto.ProprietarioResumoDTO;
import br.com.estacioneja.modules.vinculo.dto.VeiculoResumoDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoCardUsuarioDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoConsultaGuaritaDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoLinhaAdminDTO;

@Component
public class VinculoMapper {

    public VinculoCardUsuarioDTO toCardUsuario(Vinculo vinculo) {
        if (vinculo == null) return null;
        return new VinculoCardUsuarioDTO(
                vinculo.getId(),
                toVeiculoResumo(vinculo.getVeiculo()),
                toEstacionamentoCardUsuario(vinculo.getEstacionamento())
        );
    }

    public List<VinculoCardUsuarioDTO> toCardUsuarioList(List<Vinculo> vinculos) {
        if (vinculos == null) return null;
        return vinculos.stream().map(this::toCardUsuario).toList();
    }

    public VinculoLinhaAdminDTO toLinhaAdmin(Vinculo vinculo) {
        if (vinculo == null) return null;
        return new VinculoLinhaAdminDTO(
                vinculo.getId(),
                toVeiculoResumo(vinculo.getVeiculo()),
                toProprietarioResumo(vinculo.getUsuario()),
                toEstacionamentoRotulo(vinculo.getEstacionamento())
        );
    }

    public List<VinculoLinhaAdminDTO> toLinhaAdminList(List<Vinculo> vinculos) {
        if (vinculos == null) return null;
        return vinculos.stream().map(this::toLinhaAdmin).toList();
    }

    public VinculoConsultaGuaritaDTO toConsultaGuarita(Vinculo vinculo) {
        if (vinculo == null || vinculo.getVeiculo() == null) return null;
        Veiculo v = vinculo.getVeiculo();
        return new VinculoConsultaGuaritaDTO(v.getPlaca(), v.getModelo(), v.getCor());
    }

    /* sub-mappers privados (views específicas, vivem no contexto vinculo) */

    private VeiculoResumoDTO toVeiculoResumo(Veiculo v) {
        if (v == null) return null;
        return new VeiculoResumoDTO(
                v.getId(),
                v.getPlaca(),
                v.getModelo(),
                v.getCor(),
                v.getTipoVeiculo(),
                v.getObservacao()
        );
    }

    private EstacionamentoCardUsuarioDTO toEstacionamentoCardUsuario(Estacionamento e) {
        if (e == null) return null;
        Empresa empresa = e.getEmpresa();
        Endereco endereco = empresa == null ? null : empresa.getEndereco();
        return new EstacionamentoCardUsuarioDTO(
                e.getId(),
                e.getDescricao(),
                empresa == null ? null : empresa.getNome(),
                endereco == null ? null : endereco.getCidade(),
                endereco == null ? null : endereco.getUf(),
                e.getCapacidade(),
                e.getCapacidadeDisponivel()
        );
    }

    private EstacionamentoRotuloDTO toEstacionamentoRotulo(Estacionamento e) {
        if (e == null) return null;
        return new EstacionamentoRotuloDTO(e.getId(), e.getDescricao());
    }

    private ProprietarioResumoDTO toProprietarioResumo(Usuario u) {
        if (u == null) return null;
        return new ProprietarioResumoDTO(u.getId(), u.getName(), u.getEmail());
    }
}
