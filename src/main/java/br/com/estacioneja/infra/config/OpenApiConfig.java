package br.com.estacioneja.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("EstacioneJá API")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de estacionamentos inteligentes.

                                Permite controle de empresas, estacionamentos, veículos, vínculos,
                                equipamentos e registros de entrada/saída.

                                **Autenticação:** A maioria dos endpoints requer um token JWT.
                                Obtenha o token via `POST /api/v1/auth/login` e informe-o no header
                                `Authorization: Bearer {token}`.

                                **Roles:**
                                - `ADMIN` (TipoUsuario = ADMINISTRATIVO): acesso total
                                - `USER` (TipoUsuario = COMUM): acesso limitado a recursos próprios
                                """)
                        .contact(new Contact()
                                .name("EstacioneJá")
                                .email("contato@estacioneja.com.br")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT obtido no endpoint `/api/v1/auth/login`.")));
    }
}
