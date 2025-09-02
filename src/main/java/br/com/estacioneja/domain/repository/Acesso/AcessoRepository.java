package br.com.estacioneja.domain.repository.Acesso;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import java.util.List;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;


@Repository
public interface AcessoRepository extends JpaRepository<Acesso, UUID> {
    List<Acesso> findAllByEmpresa(Empresa empresa);
    Acesso findByUsuarioAndEmpresa(Usuario usuario, Empresa empresa);
}
