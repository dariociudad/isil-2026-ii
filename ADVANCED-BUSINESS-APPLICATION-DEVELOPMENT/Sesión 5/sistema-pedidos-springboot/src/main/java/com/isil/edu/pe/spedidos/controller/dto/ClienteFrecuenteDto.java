package com.isil.edu.pe.spedidos.controller.dto;

import com.isil.edu.pe.spedidos.service.domain.ClienteFrecuente;
import java.math.BigDecimal;

public record ClienteFrecuenteDto(
        String cliente,
        long totalPedidos,
        long totalUnidades,
        BigDecimal importeAcumulado,
        BigDecimal ticketPromedio) {

    public static ClienteFrecuenteDto from(ClienteFrecuente c) {
        return new ClienteFrecuenteDto(
                c.cliente(),
                c.totalPedidos(),
                c.totalUnidades(),
                c.importeAcumulado(),
                c.ticketPromedio());
    }
}
