package br.com.estacioneja.domain.model.Usuario;

import br.com.estacioneja.dto.UsuarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String senha;
    private String cpf;

    public Usuario(UsuarioDTO dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.senha = dto.senha();
        this.cpf = dto.cpf();
    }
}
