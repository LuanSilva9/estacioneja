package br.com.estacioneja.services.Vinculo;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.repository.Vinculo.VinculoRepository;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.exceptions.custom.DuplicateException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.VinculoMapper;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.services.Veiculo.VeiculoService;
import br.com.estacioneja.usecases.interfaces.IVinculo;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class VinculoService implements IVinculo {

    private final VinculoRepository vinculoRepository;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;
    private final VinculoMapper vinculoMapper;

    /* TRANSAÇÕES */

    @Override
    @Transactional
    public VinculoOutputDTO create(VinculoDTO dto) {
        Veiculo veiculo = getVeiculo(dto.placaVeiculo());
        Estacionamento estacionamento = getEstacionamento(dto.estacionamentoId());

        validateDuplicate(veiculo, estacionamento);

        Vinculo vinculo = new Vinculo(
                estacionamento,
                veiculo.getUsuario(),
                veiculo
        );

        return vinculoMapper.toDto(vinculoRepository.save(vinculo));
    }

    @Override
    @Transactional
    public void update(UUID id, VinculoDTO dto) {
        Vinculo vinculo = findEntityById(id);

        Estacionamento estacionamento = getEstacionamento(dto.estacionamentoId());

        vinculo.setEstacionamento(estacionamento);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        vinculoRepository.delete(findEntityById(id));
    }

    /* CONSULTAS */

    @Override
    public Vinculo findEntityById(UUID id) {
        return vinculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vinculo não encontrado"));
    }

    @Override
    public VinculoOutputDTO findById(UUID id) {
        return vinculoMapper.toDto(findEntityById(id));
    }

    @Override
    public List<VinculoOutputDTO> findVincleByUser(Usuario usuario) {
        return vinculoMapper.toDtoList(vinculoRepository.findAllByUsuario(usuario));
    }

    @Override
    public List<VinculoOutputDTO> findVincleByEmpresa(UUID empresaId) {
        return vinculoMapper.toDtoList(vinculoRepository.findByEmpresaId(empresaId));
    }

    public VinculoOutputDTO findVincleByPlacaAndEstacionamentoId(String placa, UUID estacionamentoId) {
        Vinculo vinculo = vinculoRepository.findByEstacionamentoIdAndVeiculoPlaca(estacionamentoId, placa).orElseThrow(() -> new EntityNotFoundException("Vinculo não encontrado"));
        
        return vinculoMapper.toDto(vinculo);
    }
    
    /* VALIDAÇÕES */
    
    public Boolean existsVinculo(String placa, UUID estacionamentoId) {
        return vinculoRepository.existsByEstacionamentoIdAndVeiculoPlaca(estacionamentoId, placa);
    }

    private void validateDuplicate(Veiculo veiculo, Estacionamento estacionamento) {
        if (vinculoRepository.existsByEstacionamentoIdAndVeiculoPlaca(estacionamento.getId(), veiculo.getPlaca())) {
            throw new DuplicateException("Esse veículo já está vinculado nesse estacionamento");
        }
    }

    /* HELPERS */

    private Veiculo getVeiculo(String placa) {
        return veiculoService.findByPlaca(placa);
    }

    private Estacionamento getEstacionamento(UUID id) {
        return estacionamentoService.findEntityById(id);
    }
}