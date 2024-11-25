package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;

public class CategoriaProducto {
    @Id 
    private int codigo;
    private String nombre;
    private String descripcion;
    private String caracteristicasDeAlmacenamiento;

    public CategoriaProducto(int codigo, String nombre, String descripcion, String caracteristicasDeAlmacenamiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caracteristicasDeAlmacenamiento = caracteristicasDeAlmacenamiento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCaracteristicasDeAlmacenamiento() {
        return caracteristicasDeAlmacenamiento;
    }

    public void setCaracteristicasDeAlmacenamiento(String caracteristicasDeAlmacenamiento) {
        this.caracteristicasDeAlmacenamiento = caracteristicasDeAlmacenamiento;
    }
    
    
}
