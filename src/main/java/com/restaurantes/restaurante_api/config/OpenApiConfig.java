package com.restaurantes.restaurante_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Restaurante")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de restaurante - Sistema completo de pedidos, estoque, funcionários e mesas")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("contato@restaurante.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .tags(List.of(
                        new Tag().name("Categorias").description("API para gerenciamento de categorias de produtos"),
                        new Tag().name("Produtos").description("API para gerenciamento de produtos do cardápio"),
                        new Tag().name("Produto com Ficha Técnica").description("API para produto e ficha técnica em uma única operação (GET/PUT/DELETE por ID)"),
                        new Tag().name("Mesas").description("API para gerenciamento de mesas do restaurante"),
                        new Tag().name("Insumos").description("API para gerenciamento de insumos/ingredientes"),
                        new Tag().name("Ficha Técnica").description("API para gerenciamento de fichas técnicas (receitas) dos produtos"),
                        new Tag().name("Estoque").description("API para gerenciamento de estoque e validade de insumos"),
                        new Tag().name("Pedidos").description("API para gerenciamento de pedidos do restaurante"),
                        new Tag().name("Funcionários").description("API para gerenciamento de funcionários")
                ));
    }
}
