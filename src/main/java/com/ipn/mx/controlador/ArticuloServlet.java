package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.ArticulosDAO;
import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dto.ArticulosDTO;
import com.ipn.mx.utilerias.Utileria;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ArticuloServlet", urlPatterns = {"/ArticuloServlet"})
public class ArticuloServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        
        if (accion.equals("listaArticulos")) {
            listadoArticulos(request, response);
        } else {
            if (accion.equals("nuevo")) {
                crearArticulo(request, response);
            } else {
                if (accion.equals("actualizar")) {
                    actualizarArticulo(request, response);
                } else {
                    if (accion.equals("eliminar")) {
                        eliminarArticulo(request, response);
                    } else {
                        if (accion.equals("guardar")) {
                            almacenarArticulo(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                mostrarArticulo(request, response);
                            } else if(accion.equals("enviar")) {
                                enviarCorreo(request, response);
                            }
                        }
                    }
                }
            }
        }
        /*
        try (PrintWriter out = response.getWriter()) {
             TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Articulo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + accion + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listadoArticulos(HttpServletRequest request, HttpServletResponse response) {
        ArticulosDAO dao = new ArticulosDAO();
        try {
            List lista = dao.readAll();
            request.setAttribute("listado", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/articulo/listaDeArticulos.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearArticulo(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        RequestDispatcher rd = request.getRequestDispatcher("/articulo/articuloForm.jsp");
        try {
            List lista = dao.readAll();
            request.setAttribute("lista", lista);
            rd.forward(request, response);
        } catch (ServletException | IOException | SQLException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarArticulo(HttpServletRequest request, HttpServletResponse response) {
        ArticulosDAO dao = new ArticulosDAO();
        ArticulosDTO dto = new ArticulosDTO();
        dto.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("id")));

        try {
            dto = dao.read(dto);
            request.setAttribute("dto", dto);
            RequestDispatcher rd = request.getRequestDispatcher("/articulo/articuloForm.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarArticulo(HttpServletRequest request, HttpServletResponse response) {
        ArticulosDAO dao = new ArticulosDAO();
        ArticulosDTO dto = new ArticulosDTO();
        dto.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("id")));

        try {
            dto = dao.read(dto);
            dao.delete(dto);
            listadoArticulos(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarArticulo(HttpServletRequest request, HttpServletResponse response) {
        ArticulosDAO dao = new ArticulosDAO();
        ArticulosDTO dto = new ArticulosDTO();
        int id = Integer.parseInt(request.getParameter("txtId"));

        if (id == 0) {   // crear
            dto.getEntidad().setNombreArticulo(request.getParameter("txtNombre"));
            dto.getEntidad().setDescripcionArticulo(request.getParameter("txtDescripcion"));
            dto.getEntidad().setExistencias(Integer.parseInt(request.getParameter("txtExistencias")));
            dto.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("txtSMin")));
            dto.getEntidad().setStockMaximo(Integer.parseInt(request.getParameter("txtSMax")));
            dto.getEntidad().setPrecio(Double.parseDouble(request.getParameter("txtPrecio")));
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("txtCategoria")));
            
            try {
                dao.create(dto);
                listadoArticulos(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{ // Actualizar
            dto.getEntidad().setIdArticulo(id);
            dto.getEntidad().setNombreArticulo(request.getParameter("txtNombre"));
            dto.getEntidad().setDescripcionArticulo(request.getParameter("txtDescripcion"));
            dto.getEntidad().setExistencias(Integer.parseInt(request.getParameter("txtExistencias")));
            dto.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("txtSMin")));
            dto.getEntidad().setStockMaximo(Integer.parseInt(request.getParameter("txtSMax")));
            dto.getEntidad().setPrecio(Double.parseDouble(request.getParameter("txtPrecio")));
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("txtCategoria")));
            
            try {
                dao.update(dto);
                listadoArticulos(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void mostrarArticulo(HttpServletRequest request, HttpServletResponse response) {
        ArticulosDAO dao = new ArticulosDAO();
        ArticulosDTO dto = new ArticulosDTO();
        dto.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("id")));

        try {
            dto = dao.read(dto);
            request.setAttribute("articulo", dto);
            RequestDispatcher rd = request.getRequestDispatcher("/articulo/verArticulo.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarCorreo(HttpServletRequest request, HttpServletResponse response) {
        Utileria utileria = new Utileria();
        String correo = request.getParameter("txtDestino");
        String asunto = request.getParameter("txtAsunto");
        String cuerpo = request.getParameter("txtCuerpo");
        
        utileria.enviarMail(correo, asunto, cuerpo);
        listadoArticulos(request, response);
    }

}
