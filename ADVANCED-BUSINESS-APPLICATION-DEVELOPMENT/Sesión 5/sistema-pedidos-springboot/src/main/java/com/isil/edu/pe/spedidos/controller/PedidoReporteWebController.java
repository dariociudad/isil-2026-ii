package com.isil.edu.pe.spedidos.controller;

import com.isil.edu.pe.spedidos.service.PedidoReporteService;
import com.isil.edu.pe.spedidos.service.domain.ResumenPedidos;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador MVC de la vista de reportes.
 * Delega toda la lógica y validación al Service.
 */
@Controller
@RequestMapping("/reportes")
public class PedidoReporteWebController {

    private final PedidoReporteService reporteService;

    public PedidoReporteWebController(PedidoReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/pedidos")
    public String reportePedidos(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false) BigDecimal totalMinimo,
            @RequestParam(required = false) Integer minPedidos,
            Model model,
            HttpServletResponse response) {

        ResumenPedidos r = reporteService.obtenerResumen();
        model.addAttribute("resumen", Map.of(
                "totalPedidos", r.totalPedidos(),
                "totalUnidades", r.totalUnidades(),
                "importeAcumulado", r.importeAcumulado(),
                "ticketPromedio", r.ticketPromedio()));

        model.addAttribute("cliente", cliente);
        model.addAttribute("totalMinimo", totalMinimo);
        model.addAttribute("minPedidos", minPedidos);

        try {
            if (cliente != null || totalMinimo != null) {
                model.addAttribute("pedidos",
                        reporteService.buscarPedidosPorClienteYTotalMinimo(
                                cliente, totalMinimo));
            }
            if (minPedidos != null) {
                List<Map<String, Object>> clientes =
                        reporteService.listarClientesFrecuentes(minPedidos)
                                .stream()
                                .map(c -> Map.<String, Object>of(
                                        "cliente", c.cliente(),
                                        "totalPedidos", c.totalPedidos(),
                                        "totalUnidades", c.totalUnidades(),
                                        "importeAcumulado", c.importeAcumulado(),
                                        "ticketPromedio", c.ticketPromedio()))
                                .toList();
                model.addAttribute("clientes", clientes);
            }
        } catch (IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            model.addAttribute("error", ex.getMessage());
        }

        return "reportes-pedidos";
    }
}
