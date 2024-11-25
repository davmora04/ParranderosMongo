package uniandes.edu.co.demo.modelo;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class RecepcionProducto {
    @Id
    private int id;
    private Date fechaRecepcion; 
    private int cantidadRecibida;
    private  int CostoUnitario;
    
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
    public int getCantidadRecibida() {
        return cantidadRecibida;
    }
    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }
    public int getCostoUnitario() {
        return CostoUnitario;
    }
    public void setCostoUnitario(int costoUnitario) {
        CostoUnitario = costoUnitario;
    }
    public RecepcionProducto(Date fechaRecepcion, int cantidadRecibida, int costoUnitario) {
        this.fechaRecepcion = fechaRecepcion;
        this.cantidadRecibida = cantidadRecibida;
        CostoUnitario = costoUnitario;
    } 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    

    
    
}
