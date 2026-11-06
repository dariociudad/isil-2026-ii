package com.isil.edu.pe.spedidos.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
class PedidoReportePa3SecurityTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void reportePedidos_conRolConsulta_respondeOkYMuestraVista() throws Exception {
        mockMvc.perform(get("/reportes/pedidos").with(user("consulta").roles("CONSULTA")))
                .andExpect(status().isOk())
                .andExpect(view().name("reportes-pedidos"));
    }

    @Test
    void reportePedidos_conUsuarioAnonimo_redirigeAutenticacion() throws Exception {
        mockMvc.perform(get("/reportes/pedidos"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void registrarPedido_conRolConsultaYCsrfValido_esRechazadoPorAutorizacion() throws Exception {
        mockMvc.perform(post("/pedidos").with(csrf()).with(user("consulta").roles("CONSULTA")))
                .andExpect(status().isForbidden());
    }

    @Test
    void clientesFrecuentes_conMinPedidosCero_respondeBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/pedidos/clientes-frecuentes")
                        .param("minPedidos", "0")
                        .with(user("consulta").roles("CONSULTA")))
                .andExpect(status().isBadRequest());
    }
}
