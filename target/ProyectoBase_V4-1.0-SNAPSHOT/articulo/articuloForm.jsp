<%-- 
    Document   : articuloForm
    Created on : 15/06/2022, 03:03:40 PM
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
        <title>Articulo Form</title>
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
                        Formulario para datos del articulo
                    </div>
                </div>
                <div class="card-body">
                    <form action="ArticuloServlet?accion=guardar" method="post">
                        <c:choose>
                            <c:when test="${dto.entidad.idArticulo == null}">
                                <div class="mb-3">
                                    <label for="txtId" class="form-label">ID articulo</label>
                                    <input type="text" class="form-control" id="txtId"
                                           placeholder="ID del articulo" name="txtId"
                                           disabled value="0">
                                </div>
                                <input type="hidden" name="txtId" value="0">
                            </c:when>
                            <c:otherwise>
                                <div class="mb-3">
                                    <label for="txtId" class="form-label">ID articulo</label>
                                    <input type="text" class="form-control" id="txtId"
                                           placeholder="ID del articulo" name="txtId"
                                           required value="${dto.entidad.idArticulo}">
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="mb-3">
                            <label for="txtNombre" class="form-label">Nombre articulo</label>
                            <input type="text" class="form-control" id="txtNombre"
                                   placeholder="Nombre del articulo" name="txtNombre"
                                   required value="${dto.entidad.nombreArticulo}">
                        </div>
                        <div class="mb-3">
                            <label for="txtDescripcion" class="form-label">Descripcion del articulo</label>
                            <input type="text" class="form-control" id="txtDescripcion"
                                   placeholder="Descripcion del articulo" name="txtDescripcion" 
                                   required value="${dto.entidad.descripcionArticulo}">
                        </div>
                        <div class="mb-3">
                            <label for="txtExistencias" class="form-label">Existencias del articulo</label>
                            <input type="number" class="form-control" id="txtExistencias"
                                   placeholder="Existencias del articulo" name="txtExistencias" 
                                   required value="${dto.entidad.existencias}"
                                   min="10" max="100">
                        </div>
                        <div class="mb-3">
                            <label for="txtSMin" class="form-label">Stock min del articulo</label>
                            <input type="number" class="form-control" id="txtSMin"
                                   placeholder="Stock min del articulo" name="txtSMin" 
                                   required value="${dto.entidad.stockMinimo}">
                        </div>
                        <div class="mb-3">
                            <label for="txtSMax" class="form-label">Stock max del articulo</label>
                            <input type="number" class="form-control" id="txtSMax"
                                   placeholder="Stock max del articulo" name="txtSMax" 
                                   required value="${dto.entidad.stockMaximo}">
                        </div>
                        <div class="mb-3">
                            <label for="txtPrecio" class="form-label">Precio del articulo</label>
                            <input type="text" class="form-control" id="txtPrecio"
                                   placeholder="Precio del articulo" name="txtPrecio" 
                                   required value="${dto.entidad.precio}" pattern="[0-9]{2,4}[\.,]([0-9]{1,2})">
                        </div>
                        <div class="mb-3">
                            <label for="txtCategoria" class="form-label">Categoria del articulo</label>
                            <select class="form-select" id="txtCategoria" name='txtCategoria' required>
                                <c:choose>
                                    <c:when test="${lista != null}">
                                        <c:forEach var="var" items="${lista}">
                                            <option value="${var.entidad.idCategoria}">${var.entidad.nombreCategoria}</option>
                                        </c:forEach>
                                    </c:when>
                                        <c:otherwise>
                                            <option selected value="${dto.entidad.idCategoria}">${dto.entidad.idCategoria}</option>
                                        </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                        <div class="mb-3">
                            <input type="submit" class="btn btn-outline-primary" value="Registrar" id="btnEnviar" placeholder="Descripcion del articulo" name="btnEnviar" required>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
