package com.isil.edu.pe.spedidos.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.isil.edu.pe.spedidos.entity.Pedido;
import com.isil.edu.pe.spedidos.repository.projection.ClienteFrecuenteProjection;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PedidoReportePa3RepositoryTest {

    @Autowired
    private PedidoReporteJdbcRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void limpiarYPrepararDatos() {
        // Arrange
        jdbcTemplate.update("DELETE FROM pedido");
        insertar("Ana Torres", 1, "100.00");
        insertar("Ana Torres", 3, "300.00");
        insertar("Luis Perez", 1, "500.00");
    }

    @Test
    void buscarPedidosPorClienteYTotalMinimo_conFiltrosValidos_retornaSoloPedidosQueCumplen() {
        // Act
        List<Pedido> resultado = repository
                .buscarPedidosPorClienteYTotalMinimo("ana", new BigDecimal("200"));

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Ana Torres", resultado.get(0).getCliente());
        assertEquals(0, new BigDecimal("300.00").compareTo(resultado.get(0).getTotal()));
    }

    @Test
    void listarClientesFrecuentes_conMinPedidosDos_retornaSoloClienteQueCumpleConAgregados() {
        // Act
        List<ClienteFrecuenteProjection> resultado =
                repository.listarClientesFrecuentes(2);

        // Assert
        assertEquals(1, resultado.size());
        ClienteFrecuenteProjection ana = resultado.get(0);
        assertEquals("Ana Torres", ana.cliente());
        assertEquals(2L, ana.totalPedidos());
        assertEquals(4L, ana.totalUnidades());
        assertEquals(0, new BigDecimal("400.00").compareTo(ana.importeAcumulado()));
        assertEquals(0, new BigDecimal("200.00").compareTo(ana.ticketPromedio()));
    }

    private void insertar(String cliente, int cantidad, String total) {
        jdbcTemplate.update("""
        INSERT INTO pedido (cliente, producto_codigo, producto_nombre,
            precio_unitario, cantidad, total, fecha)
        VALUES (?, 'LAP-001', 'Laptop', 100.00, ?, ?, CURRENT_TIMESTAMP)
        """, cliente, cantidad, new BigDecimal(total));
    }
}
