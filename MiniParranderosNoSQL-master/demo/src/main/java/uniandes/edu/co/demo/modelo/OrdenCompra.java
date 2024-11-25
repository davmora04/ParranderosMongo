package uniandes.edu.co.demo.modelo;
import java.util.Date;

import lombok.ToString;

@ToString
public class OrdenCompra {
    private String precioAcordado; 
    private long cantidad; 
    private Date fechaEntrega;
    private String estado;

    public String getPrecioAcordado() {
        return precioAcordado;
    }
    public void setPrecioAcordado(String precioAcordado) {
        this.precioAcordado = precioAcordado;
    }
    public long getCantidad() {
        return cantidad;
    }
    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
    public Date getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    } 

    public OrdenCompra(String precioAcordado, long cantidad, Date fechaEntrega, String estado) {
        this.precioAcordado = precioAcordado;
        this.cantidad = cantidad;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }
    
    
    
    
}
