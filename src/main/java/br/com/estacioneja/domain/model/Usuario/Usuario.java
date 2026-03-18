package br.com.estacioneja.domain.model.Usuario;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.estacioneja.domain.enums.TipoUsuario;
import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.input.UsuarioDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String senha;
    
    @Column(unique = true)
    private String cpf;

    @Column(unique = false)
    private String telefone;



    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("relacao-veiculo-usuario")
    private List<Veiculo> veiculos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("relacao-vinculo-usuario")
    private List<Vinculo> vinculos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonBackReference("relacao-acesso-usuario")
    private List<Acesso> acessos;

    public Usuario(UsuarioDTO dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.senha = dto.senha();
        this.cpf = dto.cpf();
        this.telefone = dto.telefone();
        this.tipoUsuario = dto.tipoUsuario();
    }

     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = switch(this.tipoUsuario) {
            case COMUM -> "ROLE_USER";
            case ADMINISTRATIVO -> "ROLE_ADMIN";
        };

        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
