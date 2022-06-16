<%-- 
    Document   : correoForm
    Created on : 15/06/2022, 06:18:19 PM
    Author     : crdaf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" ></script>
        <title>Correo</title>
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
                        Formulario para enviar correo
                    </div>
                </div>
                <div class="card-body">
                    <form action="ArticuloServlet?accion=enviar" method="post">
                        <div class="mb-3">
                            <label for="txtDestino" class="form-label">Correo destinatario</label>
                            <input type="email" class="form-control" id="txtDestino"
                                   placeholder="Correo destino" name="txtDestino" 
                                   required>
                        </div>
                        <div class="mb-3">
                            <label for="txtAsunto" class="form-label">Asunto</label>
                            <input type="text" class="form-control" id="txtAsunto"
                                   placeholder="Asunto del correo" name="txtAsunto" 
                                   required>
                        </div>
                        <div class="mb-3">
                            <label for="txtCuerpo" class="form-label">Cuerpo del mensaje</label>
                            <!--<input type="text" class="form-control" id="txtCuerpo"
                                   placeholder="Cuerpo del mensaje" name="txtCuerpo" 
                                   required>-->
                            <textarea class="form-control" id="txtCuerpo" name="txtCuerpo" rows="5" cols="30" required>Escribe tu mensaje aqui.</textarea>
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
