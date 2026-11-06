package com.isil.edu.pe.spedidos.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

import com.isil.edu.pe.spedidos.repository.PedidoReporteJdbcRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PedidoReporteServicePa3Test {

    @Mock
    private PedidoReporteJdbcRepository jdbcRepository;

    @InjectMocks
    private PedidoReporteServiceImpl service;

    @Test
    void listarClientesFrecuentes_conMinPedidosCero_lanzaExcepcionSinConsultarRepositorio() {
        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> service.listarClientesFrecuentes(0));
        verifyNoInteractions(jdbcRepository);
    }

    @Test
    void buscarPedidosPorClienteYTotalMinimo_conTotalNegativo_lanzaExcepcionSinConsultarRepositorio() {
        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> service.buscarPedidosPorClienteYTotalMinimo("Ana", new BigDecimal("-1")));
        verifyNoInteractions(jdbcRepository);
    }

    @Test
    void buscarPedidosPorClienteYTotalMinimo_conClienteVacio_lanzaExcepcionSinConsultarRepositorio() {
        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> service.buscarPedidosPorClienteYTotalMinimo("  ", BigDecimal.TEN));
        verifyNoInteractions(jdbcRepository);
    }
}
