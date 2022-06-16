<%-- 
    Document   : categoriaForm
    Created on : 7/04/2022, 09:50:17 AM
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
        <title>Categoria Form</title>
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
            <div class="card text-primary">
                <div class="card-header">
                    <div class="card-title text-center">
                        Formulario para datos de la categoria
                    </div>
                </div>
                <div class="card-body">
                    <form action="CategoriaServlet?accion=guardar" method="post">
                        <c:choose>
                            <c:when test="${dto.entidad.idCategoria == null}">
                                <div class="mb-3">
                                    <label for="txtId" class="form-label">ID categoria</label>
                                    <input type="text" class="form-control" id="txtId"
                                           placeholder="ID de la categoria" name="txtId"
                                           disabled value="0">
                                </div>
                                <input type="hidden" name="txtId" value="0">
                            </c:when>
                            <c:otherwise>
                                <div class="mb-3">
                                    <label for="txtId" class="form-label">ID categoria</label>
                                    <input type="text" class="form-control" id="txtId"
                                           placeholder="ID de la categoria" name="txtId"
                                           required value="${dto.entidad.idCategoria}">
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="mb-3">
                            <label for="txtNombre" class="form-label">Nombre categoria</label>
                            <input type="text" class="form-control" id="txtNombre"
                                   placeholder="Nombre de la categoria" name="txtNombre"
                                   required value="${dto.entidad.nombreCategoria}">
                        </div>
                        <div class="mb-3">
                            <label for="txtDescripcion" class="form-label">Descripcion de la categoria</label>
                            <input type="text" class="form-control" id="txtDescripcion"
                                   placeholder="Descripcion de la categoria" name="txtDescripcion" 
                                   required value="${dto.entidad.descripcionCategoria}">
                        </div>
                        <div class="mb-3">
                            <input type="submit" class="btn btn-outline-primary" value="Registrar" id="btnEnviar" placeholder="Descripcion de la categoria" name="btnEnviar" required>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

