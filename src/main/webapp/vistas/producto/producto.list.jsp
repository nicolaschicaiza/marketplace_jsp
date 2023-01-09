<div class="row">
    <!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->
    <div class="container">
        <h3 class="text-center">Lista de Productos</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Nuevo Producto</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Precio</th>
            </tr>
            </thead>
            <tbody>

            <!-- for (Todo todo: todos) { -->
            <c:forEach var="producto" items="${listProducto}">
                <tr>
                    <td>
                        <c:out value="${producto.id}" />
                    </td>
                    <td>

                        <c:out value="${producto.name}" />
                    </td>
                    <td>

                        <c:out value="${producto.price}" />
                    </td>
                    <td><a href="producto/edit?id=<c:out value='${producto.id}' />">Editar</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a href="producto/delete?id=<c:out value='${producto.id}' />">Eliminar</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>
        </table>
    </div>
</div>
