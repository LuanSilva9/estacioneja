package br.com.estacioneja.services.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.dto.EstacionamentoDTO;
import br.com.estacioneja.services.Vaga.VagaService;
import jakarta.transaction.Transactional;

@Service
public class EstacionamentoService {
    @Autowired private EstacionamentoRepository estacionamentoRepository;
    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private VagaService vagaService;

    @Transactional
    public List<Estacionamento> listEstacionamentos() {
        return estacionamentoRepository.findAll();
    }

    @Transactional
    public List<Estacionamento> listEstacionamentosByCompany(Long id) throws Exception {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new Exception("Empresa não encontrada"));

        return estacionamentoRepository.findAllByEmpresa(empresa);
    }
    
    @Transactional
    public Estacionamento listEstacionamentoById(UUID id) throws Exception {
        return estacionamentoRepository.findById(id).orElseThrow(() -> new Exception("Estacionamento não encontrado"));
    }

    @Transactional
    public Estacionamento createEstacionamento(EstacionamentoDTO dto) throws Exception  {
        Empresa empresa = empresaRepository.findById(dto.empresaId()).orElseThrow(() -> new Exception("Empresa não encontrada"));

        Estacionamento newEstacionamento = new Estacionamento(dto, empresa);

        for(Long i = 0l; i < newEstacionamento.getCapacidadeTotal(); i++) {
            String slug = empresa.getPrefixo() + "-" + newEstacionamento.getPrefixo() + "-" + i;

            vagaService.criarVaga(newEstacionamento, slug);
        }        

        return estacionamentoRepository.save(newEstacionamento);
    }

    @Transactional
    public Estacionamento updateEstacionamento(UUID id, EstacionamentoDTO dto) throws Exception {
        Empresa empresa = empresaRepository.findById(dto.empresaId()).orElseThrow(() -> new Exception("Empresa não encontrada"));

        Estacionamento estacionamento = estacionamentoRepository.findById(id).orElseThrow(() -> new Exception("Estacionamento não Encontrado"));

        estacionamento.setEmpresa(empresa);
        estacionamento.setCapacidadeTotal(dto.capacidade());
        estacionamento.setStatusEstacionamento(dto.statusEstacionamento());

        return estacionamentoRepository.save(estacionamento);
    }

    @Transactional
    public Estacionamento deleteEstacionamento(UUID id) throws Exception {
        Estacionamento estacionamento = estacionamentoRepository.findById(id).orElseThrow(() -> new Exception("Estacionamento não Encontrado"));

        estacionamentoRepository.delete(estacionamento);

        return estacionamento;
    }
}
