package com.ipn.mx.modelo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatosGraficaDTO {
    private int cantidad;
    private String nombre;
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Cantidad: ").append(cantidad).append("\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        
        return sb.toString();
    }
}
