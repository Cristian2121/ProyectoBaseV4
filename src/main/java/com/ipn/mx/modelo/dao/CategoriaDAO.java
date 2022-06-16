package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.CategoriaDTO;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaDAO {
    private static final String SQL_INSERT = "insert into Categoria (nombreCategoria, descripcionCategoria) values(?,?)";
    private static final String SQL_UPDATE = "update Categoria set nombreCategoria=?, descripcionCategoria=? where idCategoria=?";
    private static final String SQL_DELETE = "delete from Categoria where idCategoria=?";
    private static final String SQL_READ = "select * from Categoria where idCategoria=?";
    private static final String SQL_READ_ALL = "select * from Categoria";
    
    private Connection conexion;
    
    public Connection obtenerConexion() {
        //obtener conexion
        String usuario = "ecrzhkajqyzwya";
        String clave = "bc1366515b508539bf7c58a0b64882c84b7de3408a8cbf3a04c08f5cafed7b1e";
        String url = "jdbc:postgresql://ec2-54-147-33-38.compute-1.amazonaws.com:5432/d5q6ornf6f88c9?sslmode=require";
        String driverBD = "org.postgresql.Driver";

        try {
            Class.forName(driverBD);
            conexion = DriverManager.getConnection(url, usuario, clave);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conexion;
    }
    
    public void create(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        
        try{
            ps = conexion.prepareStatement(SQL_INSERT);
            ps.setString(1, dto.getEntidad().getNombreCategoria());
            ps.setString(2, dto.getEntidad().getDescripcionCategoria());
            ps.executeUpdate();
        }finally{
            if(ps != null){
                ps.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
    }
    
    public void update(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        
        try{
            ps = conexion.prepareStatement(SQL_UPDATE);
            ps.setString(1, dto.getEntidad().getNombreCategoria());
            ps.setString(2, dto.getEntidad().getDescripcionCategoria());
            ps.setInt(3, dto.getEntidad().getIdCategoria());
            ps.executeUpdate();
        }finally{
            if(ps != null){
                ps.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
    }
    
    public void delete(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        
        try{
            ps = conexion.prepareCall(SQL_DELETE);
            ps.setInt(1, dto.getEntidad().getIdCategoria());
            ps.executeUpdate();
        }finally{
            if(ps != null){
                ps.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
    }
    
    public CategoriaDTO read(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            ps = conexion.prepareStatement(SQL_READ);
            ps.setInt(1, dto.getEntidad().getIdCategoria());
            rs = ps.executeQuery();
            
            List resultados = obtenerResultados(rs);
            
            if(resultados.size() > 0){
                return (CategoriaDTO) resultados.get(0);
            } else{
                return null;
            }
        }finally{
            if(ps != null){
                ps.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
    }
    
    public List readAll() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            ps = conexion.prepareStatement(SQL_READ_ALL);
            rs = ps.executeQuery();
            
            List resultados = obtenerResultados(rs);
            
            if(resultados.size() > 0){
                return resultados;
            } else{
                return null;
            }
        }finally{
            if(ps != null){
                ps.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
    }
    
    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        
        while(rs.next()){            
            CategoriaDTO dto = new CategoriaDTO();
            dto.getEntidad().setIdCategoria(rs.getInt("idCategoria"));
            dto.getEntidad().setNombreCategoria(rs.getString("nombreCategoria"));
            dto.getEntidad().setDescripcionCategoria(rs.getString("descripcionCategoria"));
            resultados.add(dto);
        }
        
        return resultados;
    }
    
    public static void main(String[] args){
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(3);
        //dto.getEntidad().setNombreCategoria("Linea blanca");
        //dto.getEntidad().setDescripcionCategoria("Articulos de linea blanca");
        
        try {
            dao.delete(dto);
            //System.out.println(dao.read(dto));
            //dao.update(dto);
            //dao.create(dto);
            //System.out.println(dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
