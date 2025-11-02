package br.com.estacioneja.domain.repository.Filial;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Solicitacao.Solicitacao;

@Repository
public interface FilialRepository extends JpaRepository<Filial, UUID> {
    Boolean existsByCnpj(String cnpj);
    List<Filial> findAllByEmpresaId(Long empresaId);

    @Query("SELECT s FROM Solicitacao s WHERE s.filial.id = :filial_id")
    List<Solicitacao> findSolicitacoesByFilial(@Param(value = "filial_id") UUID id);
}
