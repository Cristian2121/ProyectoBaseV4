<%-- 
    Document   : listaDeArticulos
    Created on : 15/06/2022, 02:44:16 PM
    Author     : crdaf
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" ></script>
        <title>Principal</title>
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#">Navbar</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="#">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="CategoriaServlet?accion=listaCategorias">Listado de categorias</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="ArticuloServlet?accion=listaArticulos">Listado de articulos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="correoForm.jsp">Enviar correo</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="mb-3"></div>
            <div class="card">
                <div class="card-header">
                    <h1 class="card-title text-primary text-center">
                        Listado de articulos
                    </h1>
                    <a href="ArticuloServlet?accion=nuevo" class="btn btn-outline-primary">Nuevo</a>
                </div>
                <div class="card-body">
                    <table class="table table-striped">
                        <tr>
                            <th>Clave articulo</th>
                            <th>Nombre articulo</th>
                            <th>Descripcion articulo</th>
                            <th>Eliminar</th>
                            <th>Actualizar</th>
                        </tr>
                        <c:forEach items="${listado}" var="dto">
                            <tr>
                                <td>
                                    <a href="ArticuloServlet?accion=ver&id=${dto.entidad.idArticulo}" class="btn btn-outline-success">
                                        <c:out value="${dto.entidad.idArticulo}" />
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${dto.entidad.nombreArticulo}" />
                                </td>
                                <td>
                                    <c:out value="${dto.entidad.descripcionArticulo}" />
                                </td>
                                <td>
                                    <a href="ArticuloServlet?accion=eliminar&id=${dto.entidad.idArticulo}" class="btn btn-outline-danger">Eliminar</a>
                                </td>
                                <td>
                                    <a href="ArticuloServlet?accion=actualizar&id=${dto.entidad.idArticulo}" class="btn btn-outline-info">Actualizar</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
