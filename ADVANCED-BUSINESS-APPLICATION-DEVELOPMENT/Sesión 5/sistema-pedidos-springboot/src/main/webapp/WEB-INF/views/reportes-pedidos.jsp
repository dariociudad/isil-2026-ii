<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Reportes de pedidos</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 24px; }
    table { border-collapse: collapse; margin: 12px 0; }
    th, td { border: 1px solid #999; padding: 6px 10px; text-align: left; }
    th { background: #eee; }
    .error { color: #b00020; font-weight: bold; }
    fieldset { margin: 16px 0; }
  </style>
</head>
<body>
  <h1>Reportes de pedidos</h1>
  <p><a href="<c:url value='/pedidos'/>">Volver a pedidos</a></p>

  <c:if test="${not empty error}">
    <p class="error">Error: <c:out value="${error}"/></p>
  </c:if>

  <h2>Resumen general</h2>
  <table>
    <tr><th>Total pedidos</th><th>Total unidades</th>
        <th>Importe acumulado</th><th>Ticket promedio</th></tr>
    <tr>
      <td><c:out value="${resumen.totalPedidos}"/></td>
      <td><c:out value="${resumen.totalUnidades}"/></td>
      <td><c:out value="${resumen.importeAcumulado}"/></td>
      <td><c:out value="${resumen.ticketPromedio}"/></td>
    </tr>
  </table>

  <fieldset>
    <legend>Búsqueda avanzada</legend>
    <form method="get" action="<c:url value='/reportes/pedidos'/>">
      Cliente: <input type="text" name="cliente" value="<c:out value='${cliente}'/>">
      Total mínimo: <input type="text" name="totalMinimo" value="<c:out value='${totalMinimo}'/>">
      <button type="submit">Buscar</button>
    </form>
    <c:if test="${pedidos != null}">
      <table>
        <tr><th>ID</th><th>Cliente</th><th>Producto</th>
            <th>Cantidad</th><th>Total</th><th>Fecha</th></tr>
        <c:forEach var="p" items="${pedidos}">
          <tr>
            <td><c:out value="${p.id}"/></td>
            <td><c:out value="${p.cliente}"/></td>
            <td><c:out value="${p.productoNombre}"/></td>
            <td><c:out value="${p.cantidad}"/></td>
            <td><c:out value="${p.total}"/></td>
            <td><c:out value="${p.fecha}"/></td>
          </tr>
        </c:forEach>
      </table>
      <c:if test="${empty pedidos}"><p>Sin resultados.</p></c:if>
    </c:if>
  </fieldset>

  <fieldset>
    <legend>Clientes frecuentes</legend>
    <form method="get" action="<c:url value='/reportes/pedidos'/>">
      Mínimo de pedidos: <input type="text" name="minPedidos" value="<c:out value='${minPedidos}'/>">
      <button type="submit">Consultar</button>
    </form>
    <c:if test="${clientes != null}">
      <table>
        <tr><th>Cliente</th><th>Pedidos</th><th>Unidades</th>
            <th>Importe acumulado</th><th>Ticket promedio</th></tr>
        <c:forEach var="c" items="${clientes}">
          <tr>
            <td><c:out value="${c.cliente}"/></td>
            <td><c:out value="${c.totalPedidos}"/></td>
            <td><c:out value="${c.totalUnidades}"/></td>
            <td><c:out value="${c.importeAcumulado}"/></td>
            <td><c:out value="${c.ticketPromedio}"/></td>
          </tr>
        </c:forEach>
      </table>
      <c:if test="${empty clientes}"><p>Sin resultados.</p></c:if>
    </c:if>
  </fieldset>
</body>
</html>
