package br.com.estacioneja.infra.config.security;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.shared.enums.TipoAcesso;

import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.usuario.UsuarioRepository;
import br.com.estacioneja.errors.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UsuarioRepository usuarioRepository;

    /* ====== AUTENTICAÇÃO ====== */

    public Usuario getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof Usuario u)) {
            throw new ForbiddenException("Autenticação requerida");
        }
        return u;
    }

    @Transactional(readOnly = true)
    public Usuario getCurrentUserWithAcessos() {
        UUID id = getCurrentUser().getId();
        return usuarioRepository.findByIdWithAcessos(id)
                .orElseThrow(() -> new ForbiddenException("Usuário autenticado não encontrado"));
    }

    /* ====== CONSULTAS NÃO-LANÇANTES ====== */

    public boolean hasEmpresaRole(Usuario usuario, UUID empresaId, TipoAcesso role) {
        if (usuario == null || empresaId == null || role == null) return false;

        return acessos(usuario).stream().anyMatch(a -> empresaId.equals(empresaIdOf(a)) && a.getTipoAcesso() == role);
    }

    public boolean hasAnyEmpresaRole(Usuario usuario, UUID empresaId, TipoAcesso... roles) {
        if (usuario == null || empresaId == null || roles == null || roles.length == 0) return false;
        List<TipoAcesso> allowed = Arrays.asList(roles);
        
        return acessos(usuario).stream().anyMatch(a ->
                empresaId.equals(empresaIdOf(a)) && allowed.contains(a.getTipoAcesso())
        );
    }

    public boolean hasEmpresaAccess(Usuario usuario, UUID empresaId) {
        if (usuario == null || empresaId == null) return false;
        return acessos(usuario).stream().anyMatch(a -> empresaId.equals(empresaIdOf(a)));
    }

    /* ====== CHECAGENS LANÇANTES (HTTP 403) ====== */

    public void requireEmpresaRole(Usuario usuario, UUID empresaId, TipoAcesso role) {
        if (!hasEmpresaRole(usuario, empresaId, role)) {
            throw new ForbiddenException(
                "Operação restrita ao cargo " + role + " na empresa alvo"
            );
        }
    }

    public void requireAnyEmpresaRole(Usuario usuario, UUID empresaId, TipoAcesso... roles) {
        if (!hasAnyEmpresaRole(usuario, empresaId, roles)) {
            throw new ForbiddenException(
                "Operação restrita aos cargos " + Arrays.toString(roles) + " na empresa alvo"
            );
        }
    }

    public void requireEmpresaAccess(Usuario usuario, UUID empresaId) {
        if (!hasEmpresaAccess(usuario, empresaId)) {
            throw new ForbiddenException("Você não possui vínculo com esta empresa");
        }
    }

    /* ====== HELPERS ====== */

    private List<Acesso> acessos(Usuario u) {
        return u.getAcessos() == null ? List.of() : u.getAcessos();
    }

    private UUID empresaIdOf(Acesso a) {
        return a.getEmpresa() == null ? null : a.getEmpresa().getId();
    }
}
