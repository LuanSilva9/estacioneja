package br.com.estacioneja.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.modules.acessos.Acesso;
import br.com.estacioneja.modules.acessos.AcessoRepository;
import br.com.estacioneja.modules.empresa.Empresa;
import br.com.estacioneja.modules.empresa.EmpresaRepository;
import br.com.estacioneja.modules.endereco.Endereco;
import br.com.estacioneja.modules.endereco.EnderecoRepository;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.usuario.UsuarioRepository;
import br.com.estacioneja.shared.enums.Plano;
import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.shared.enums.TipoEmpresa;
import br.com.estacioneja.shared.enums.TipoUsuario;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootstrapDataRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BootstrapDataRunner.class);

    private static final String ROOT_EMAIL = "root@estacioneja.com";
    private static final String ROOT_SENHA = "12345678";
    private static final String ROOT_NOME = "Root Administrador";
    private static final String ROOT_CPF = "11144477735";
    private static final String ROOT_TELEFONE = "11999999999";

    private static final String EMPRESA_NOME = "EstacioneJa Exemplo";
    private static final String EMPRESA_CNPJ = "00000000000191";
    private static final String EMPRESA_PREFIXO = "EJX";

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final EnderecoRepository enderecoRepository;
    private final AcessoRepository acessoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (usuarioRepository.existsByEmail(ROOT_EMAIL)) {
            log.info("Bootstrap: usuário {} já existe, pulando seed.", ROOT_EMAIL);
            return;
        }

        Usuario root = new Usuario(ROOT_NOME, ROOT_EMAIL, ROOT_CPF, ROOT_TELEFONE, TipoUsuario.ADMINISTRATIVO);
        root.setSenha(passwordEncoder.encode(ROOT_SENHA));
        usuarioRepository.save(root);

        Endereco endereco = new Endereco(
                "Av. Exemplo, 1000",
                "Centro",
                "São Paulo",
                "SP",
                "01000000",
                -23.55052,
                -46.633308
        );
        enderecoRepository.save(endereco);

        Empresa empresa = Empresa.criarMatriz(
                root,
                EMPRESA_NOME,
                endereco,
                TipoEmpresa.EMPRESA_DEFAULT,
                EMPRESA_CNPJ,
                EMPRESA_PREFIXO,
                Plano.FREE_TIER
        );
        empresaRepository.save(empresa);

        acessoRepository.save(new Acesso(TipoAcesso.MASTER, root, empresa));

        log.info("Bootstrap: criado usuário ADMINISTRATIVO {} e empresa exemplo {}.", ROOT_EMAIL, EMPRESA_NOME);
    }
}
