<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
  <nav class="navbar navbar-expand-md navbar-dark" style="background-color: black">
    <div>
      <a href="<%=request.getContextPath()%>/producto" class="navbar-brand"> Awayhub </a>
    </div>
    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/producto" class="nav-link">Home</a></li>
    </ul>
    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/pedido" class="nav-link">Pedidos</a></li>
    </ul>
  </nav>
</header>