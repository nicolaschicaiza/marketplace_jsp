<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/templates/template.jsp" %>
<body>
<div class="row" style="margin-left: 15px; margin-right: 15px;">
    <div class="container">
        <br>
        <h3 class="text-center">Lista de Productos</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/new_producto" class="btn btn-success">Nuevo Producto</a>
        </div>
        <br>
<%--        <p>--%>
<%--            <%=request.getAttribute("prueba")%>--%>
<%--        </p>--%>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Precio</th>
                <th>Opciones</th>
            </tr>
            </thead>
            <tbody>

            <!-- for (Todo todo: todos) { -->
            <c:forEach var="producto" items="${listProducto}">
                <tr>
                    <td>
                        <c:out value="${producto.idProducto}"/>
                    </td>
                    <td>
                        <c:out value="${producto.nombre}"/>
                    </td>
                    <td>
                        <c:out value="${producto.precio}"/>
                    </td>
                    <td>
                        <div class="d-flex justify-content-center gap">
                            <a class="btn btn-success mr-2" href="<%=request.getContextPath()%>/add_producto?id=<c:out value='${producto.idProducto}' />">
                                <i class="fa fa-cart-shopping"></i>
                            </a>
                            <a class="btn btn-primary mr-2" href="edit_producto?id=<c:out value='${producto.idProducto}' />">
                                <i class="fa fa-marker"></i>
                            </a>
                            <a class="btn btn-danger" href="<%=request.getContextPath()%>/delete_producto?id=<c:out value='${producto.idProducto}' />">
                                <i class="fa fa-trash-alt"></i>
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>
        </table>
    </div>
</div>
</body>
