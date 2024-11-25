package uniandes.edu.co.demo.modelo;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class RecepcionProducto {
    @Id
    private int id;
    private Date fechaRecepcion;
    private int cantidadRecibida;
    private int cantidadMinima; // Nueva información para definir la cantidad mínima requerida
    private int costoUnitario;

    public RecepcionProducto() {
    }

    public RecepcionProducto(Date fechaRecepcion, int cantidadRecibida, int cantidadMinima, int costoUnitario) {
        this.fechaRecepcion = fechaRecepcion;
        this.cantidadRecibida = cantidadRecibida;
        this.cantidadMinima = cantidadMinima;
        this.costoUnitario = costoUnitario;
    }

    // Getters y setters
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

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public int getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(int costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
