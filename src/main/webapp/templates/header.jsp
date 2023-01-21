<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
  <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
    <div>
      <a href="<%=request.getContextPath()%>/producto.list.jsp" class="navbar-brand"> Awayhub </a>
    </div>
    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/producto.list.jsp" class="nav-link">Home</a></li>
    </ul>
    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/vistas/pedido/pedido.list.jsp" class="nav-link">Pedidos</a></li>
    </ul>
  </nav>
</header>