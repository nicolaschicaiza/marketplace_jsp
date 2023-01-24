<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/templates/template.jsp" %>
<body>
<div class="row">
    <div class="container">
        <br>
        <h3 class="text-center">Lista de Pedidos</h3>
        <hr>
        <br>
        <%--        <p>--%>
        <%--            <%=request.getAttribute("prueba")%>--%>
        <%--        </p>--%>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>Producto</th>
                <th>Cantidad</th>
                <th>Precio</th>
            </tr>
            </thead>
            <tbody>

            <!-- for (Todo todo: todos) { -->
            <c:forEach var="pedido" items="${listPedido}">
                <tr>
                    <td>
                        <c:out value="${pedido.idPedido}"/>
                    </td>
                    <td>
                        <c:out value="${pedido.idCliente}"/>
                    </td>
                    <td>
                        <c:out value="${pedido.idProducto}"/>
                    </td>
                    <td>
                        <c:out value="${pedido.cantidad}"/>
                    </td>
                    <td>
                        <c:out value="${pedido.subtotal}"/>
                    </td>
                    <td><a href="pedido/edit?id=<c:out value='${pedido.idPedido}' />">Editar</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a href="pedido/delete?id=<c:out value='${pedido.idPedido}' />">Eliminar</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>
        </table>
    </div>
</div>
</body>
