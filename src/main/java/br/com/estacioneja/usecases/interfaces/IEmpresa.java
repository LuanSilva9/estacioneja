package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.dto.output.URLImagemOutputDTO;
import br.com.estacioneja.dto.update.EmpresaUpdateDto;
public interface IEmpresa {

    /* CRUD */
    EmpresaOutputDTO create(EmpresaDTO dto);
    void update(UUID id, EmpresaUpdateDto dto);
    void delete(UUID id);

    /* Consultas */
    Empresa findEntityById(UUID id);
    EmpresaOutputDTO findById(UUID id);
    List<EmpresaOutputDTO> findAll();

    /* Logo */
    URLImagemOutputDTO uploadLogo(UUID id, MultipartFile file, Usuario usuarioAutenticado);
    URLImagemOutputDTO getLogo(UUID id);
    void deleteLogo(UUID id, Usuario usuarioAutenticado);

    /* Banner */
    URLImagemOutputDTO uploadBanner(UUID id, MultipartFile file, Usuario usuarioAutenticado);
    URLImagemOutputDTO getBanner(UUID id);
    void deleteBanner(UUID id, Usuario usuarioAutenticado);
}
