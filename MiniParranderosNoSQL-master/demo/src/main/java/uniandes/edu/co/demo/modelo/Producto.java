package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Date;

public class Producto {

    @Id 
    private int codigoBarras;
    private String nombre;
    private int precioVenta; 
    private int unidadMedida; 
    private String presentacion;
    private int cantidadPresentacion;
    private Date fechaVencimiento;

    private List<CategoriaProducto> categoria; 

    public Producto(int codigoBarras, String nombre, int precioVenta, int unidadMedida, String presentacion, int cantidadPresentacion, Date fechaVencimiento, List<CategoriaProducto> categoria) {
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.unidadMedida = unidadMedida;
        this.presentacion = presentacion;
        this.cantidadPresentacion = cantidadPresentacion;
        this.fechaVencimiento = fechaVencimiento;
        this.categoria = categoria;
    }

    public int getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(int codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(int unidadMedida) {
        unidadMedida = unidadMedida;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getCantidadPresentacion() {
        return cantidadPresentacion;
    }

    public void setCantidadPresentacion(int cantidadPresentacion) {
        this.cantidadPresentacion = cantidadPresentacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public List<CategoriaProducto> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<CategoriaProducto> categoria) {
        this.categoria = categoria;
    }

    


    
}
