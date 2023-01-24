<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/templates/template.jsp" %>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${producto != null}">
            <form action="update" method="post">
                </c:if>

                <c:if test="${producto == null}">
                <form action="insert" method="post">
                    </c:if>
                    <caption>
                        <h2>
                            <c:if test="${producto != null}">
                                Editar Producto
                            </c:if>

                            <c:if test="${producto == null}">
                                Nuevo Producto
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${producto != null}">

                        <input type="hidden" name="id " value="<c:out value='${producto.idProducto}' />" />
                    </c:if>
                    <fieldset class="form-group">
                        <label>Nombre</label> <input type="text" value="<c:out value='${producto.nombre}' />"
                                                     class="form-control" name="name" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Precio</label> <input type="text" value="<c:out value='${producto.precio}' />"
                                                    class="form-control" name="price">
                    </fieldset>
                    <button type="submit" class="btn btn-success">Guardar</button>
                </form>
        </div>
    </div>
</div>
</body>