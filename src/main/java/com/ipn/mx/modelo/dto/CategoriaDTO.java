package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Categoria;
import java.io.Serializable;
import lombok.Data;

@Data
public class CategoriaDTO implements Serializable{
    private Categoria entidad;
    
    public CategoriaDTO(){
        entidad = new Categoria();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("idCategoria").append(getEntidad().getIdCategoria()).append("\n");
        sb.append("nombreCategoria").append(getEntidad().getNombreCategoria()).append("\n");
        sb.append("descripcionCategoria").append(getEntidad().getDescripcionCategoria()).append("\n");
        
        return sb.toString();
    }
}
