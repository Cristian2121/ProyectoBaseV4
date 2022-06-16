package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.ArticulosDTO;
import com.ipn.mx.modelo.dto.DatosGraficaDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticulosDAO {

    private static final String SQL_INSERT = "insert into Articulos (nombreArticulo, descripcionArticulo, existencias, stockMinimo, stockMaximo, precio, idCategoria) "
            + "values(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update Articulos set nombreArticulo=?,descripcionArticulo=?,existencias=?,stockMinimo=?,stockMaximo=?,"
            + "precio=?,idCategoria=? where idArticulo=?";
    private static final String SQL_DELETE = "delete from Articulos where idArticulo=?";
    private static final String SQL_READ = "select * from Articulos where idArticulo=?";
    private static final String SQL_READ_ALL = "select * from Articulos";
    private static final String SQL_GRAFICAR = "select Categoria.nombreCategoria, count(Articulos.idArticulo) as cantidad from Categoria"
            + " inner join Articulos on Categoria.idCategoria = Articulos.idCategoria group by Categoria.idCategoria";

    private Connection conexion;

    public Connection obtenerConexion() {
        //obtener conexion
        String usuario = "ecrzhkajqyzwya";
        String clave = "bc1366515b508539bf7c58a0b64882c84b7de3408a8cbf3a04c08f5cafed7b1e";
        String url = "jdbc:postgresql://ec2-54-147-33-38.compute-1.amazonaws.com:5432/d5q6ornf6f88c9";
        String driverBD = "org.postgresql.Driver";

        try {
            Class.forName(driverBD);
            conexion = DriverManager.getConnection(url, usuario, clave);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conexion;
    }

    public void create(ArticulosDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;

        try {
            cs = conexion.prepareCall(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getNombreArticulo());
            cs.setString(2, dto.getEntidad().getDescripcionArticulo());
            cs.setInt(3, dto.getEntidad().getExistencias());
            cs.setInt(4, dto.getEntidad().getStockMinimo());
            cs.setInt(5, dto.getEntidad().getStockMaximo());
            cs.setDouble(6, dto.getEntidad().getPrecio());
            cs.setInt(7, dto.getEntidad().getIdCategoria());
            cs.executeUpdate();
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void update(ArticulosDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;

        try {
            cs = conexion.prepareCall(SQL_UPDATE);
            cs.setString(1, dto.getEntidad().getNombreArticulo());
            cs.setString(2, dto.getEntidad().getDescripcionArticulo());
            cs.setInt(3, dto.getEntidad().getExistencias());
            cs.setInt(4, dto.getEntidad().getStockMinimo());
            cs.setInt(5, dto.getEntidad().getStockMaximo());
            cs.setDouble(6, dto.getEntidad().getPrecio());
            cs.setInt(7, dto.getEntidad().getIdCategoria());
            cs.setInt(8, dto.getEntidad().getIdArticulo());
            cs.executeUpdate();
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void delete(ArticulosDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;

        try {
            cs = conexion.prepareCall(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getIdArticulo());
            cs.executeUpdate();
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public ArticulosDTO read(ArticulosDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(SQL_READ);
            ps.setInt(1, dto.getEntidad().getIdArticulo());
            rs = ps.executeQuery();

            List resultados = obtenerResultados(rs);

            if (resultados.size() > 0) {
                return (ArticulosDTO) resultados.get(0);
            } else {
                return null;
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public List readAll() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(SQL_READ_ALL);
            rs = ps.executeQuery();

            List resultados = obtenerResultados(rs);

            if (resultados.size() > 0) {
                return resultados;
            } else {
                return null;
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();

        while (rs.next()) {
            ArticulosDTO dto = new ArticulosDTO();
            dto.getEntidad().setIdArticulo(rs.getInt("idArticulo"));
            dto.getEntidad().setNombreArticulo(rs.getString("nombreArticulo"));
            dto.getEntidad().setDescripcionArticulo(rs.getString("descripcionArticulo"));
            dto.getEntidad().setExistencias(rs.getInt("existencias"));
            dto.getEntidad().setStockMinimo(rs.getInt("stockMinimo"));
            dto.getEntidad().setStockMaximo(rs.getInt("stockMaximo"));
            dto.getEntidad().setPrecio(rs.getInt("precio"));
            dto.getEntidad().setIdCategoria(rs.getInt("idCategoria"));
            resultados.add(dto);
        }

        return resultados;
    }

    public List graficar() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = new ArrayList<>();

        try {
            ps = conexion.prepareStatement(SQL_GRAFICAR);
            rs = ps.executeQuery();
            
            while(rs.next()){
                DatosGraficaDTO datos = new DatosGraficaDTO();
                datos.setNombre(rs.getString("nombreCategoria"));
                datos.setCantidad(rs.getInt("cantidad"));
                lista.add(datos);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
        
        return lista;
    }

    public static void main(String[] args) {
        ArticulosDAO dao = new ArticulosDAO();
        ArticulosDTO dto = new ArticulosDTO();
        //dto.getEntidad().setIdArticulo(5);
        /*dto.getEntidad().setNombreArticulo("Laptop Dell");
        dto.getEntidad().setDescripcionArticulo("16gb ram 500 hdd ryzen 5");
        dto.getEntidad().setExistencias(10);
        dto.getEntidad().setStockMinimo(5);
        dto.getEntidad().setStockMaximo(15);
        dto.getEntidad().setPrecio(238.49); // decimal(5,2)
        dto.getEntidad().setIdCategoria(2);*/

        try {
            //dao.delete(dto);
            //System.out.println(dao.read(dto));
            //dao.update(dto);
            //dao.create(dto);
            //System.out.println(dao.readAll());
            System.out.println(dao.graficar());
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
