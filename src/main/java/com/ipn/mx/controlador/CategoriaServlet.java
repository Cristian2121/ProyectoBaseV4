package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.ArticulosDAO;
import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.modelo.dto.DatosGraficaDTO;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

@WebServlet(name = "CategoriaServlet", urlPatterns = {"/CategoriaServlet"})
public class CategoriaServlet extends HttpServlet {

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

        if (accion.equals("listaCategorias")) {
            listadoCategorias(request, response);
        } else {
            if (accion.equals("nuevo")) {
                crearCategoria(request, response);
            } else {
                if (accion.equals("actualizar")) {
                    actualizarCategoria(request, response);
                } else {
                    if (accion.equals("eliminar")) {
                        eliminarCategoria(request, response);
                    } else {
                        if (accion.equals("guardar")) {
                            almacenarCategoria(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                mostrarCategoria(request, response);
                            } else {
                                if (accion.equals("verReporte")) {
                                    mostrarReporte(request, response);
                                } else {
                                    if (accion.equals("graficar")) {
                                        mostrarGrafica(request, response);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /*
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoriaServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoriaServlet at " + request.getContextPath() + "</h1>");
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

    private void listadoCategorias(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        try {
            List lista = dao.readAll();
            System.out.println(lista);
            request.setAttribute("listado", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/categoria/listaDeCategorias.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearCategoria(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("/categoria/categoriaForm.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));

        try {
            dto = dao.read(dto);
            request.setAttribute("dto", dto);
            RequestDispatcher rd = request.getRequestDispatcher("/categoria/categoriaForm.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));

        try {
            dto = dao.read(dto);
            dao.delete(dto);
            listadoCategorias(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        int id = Integer.parseInt(request.getParameter("txtId"));

        if (id == 0) {   // crear
            dto.getEntidad().setNombreCategoria(request.getParameter("txtNombre"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("txtDescripcion"));

            try {
                dao.create(dto);
                listadoCategorias(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{ // Actualizar
            dto.getEntidad().setIdCategoria(id);
            dto.getEntidad().setNombreCategoria(request.getParameter("txtNombre"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("txtDescripcion"));

            try {
                dao.update(dto);
                listadoCategorias(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void mostrarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));

        try {
            dto = dao.read(dto);
            request.setAttribute("categoria", dto);
            RequestDispatcher rd = request.getRequestDispatcher("/categoria/verCategoria.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarReporte(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream sos = null;
        try {
            CategoriaDAO dao = new CategoriaDAO();
            File reporte;
            byte[] b;
            
            sos = response.getOutputStream();
            reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ReporteEstados.jasper"));
            b = JasperRunManager.runReportToPdf(reporte.getPath(), null, dao.obtenerConexion());
            
            response.setContentType("application/pdf");
            response.setContentLength(b.length);
            sos.write(b, 0, b.length);
            sos.flush();
            sos.close();
        } catch (IOException | JRException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sos.close();
            } catch (IOException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
    }

    private void mostrarGrafica(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JFreeChart chart = ChartFactory.createPieChart("Articulos por categoria", getDatosGrafica(), true, true, Locale.getDefault());
        String archivo = getServletConfig().getServletContext().getRealPath("/grafica.png");
        ChartUtils.saveChartAsPNG(new File(archivo), chart, 800, 600);
        
        RequestDispatcher rd = request.getRequestDispatcher("/grafica.jsp");
        rd.forward(request, response);
    }

    private PieDataset getDatosGrafica() {
        DefaultPieDataset pie = new DefaultPieDataset();
        ArticulosDAO dao = new ArticulosDAO();
        
        try {
            List datos = dao.graficar();
            
            for(int i = 0; i < datos.size(); i++){
                DatosGraficaDTO dto = (DatosGraficaDTO) datos.get(i);
                pie.setValue(dto.getNombre(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pie;
    }

}
