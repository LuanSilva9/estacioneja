package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.modules.usuario.dto.ReadFotoPerfilDto;
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
    ReadFotoPerfilDto uploadLogo(UUID id, MultipartFile file, Usuario usuarioAutenticado);
    ReadFotoPerfilDto getLogo(UUID id);
    void deleteLogo(UUID id, Usuario usuarioAutenticado);

    /* Banner */
    ReadFotoPerfilDto uploadBanner(UUID id, MultipartFile file, Usuario usuarioAutenticado);
    ReadFotoPerfilDto getBanner(UUID id);
    void deleteBanner(UUID id, Usuario usuarioAutenticado);
}
