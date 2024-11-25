package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;

public class Proveedor {
    @Id
    private int nit; 
    private String nombre;
    private String direccion;
    private String nombreContacto; 
    private int telefonoContacto;

    public Proveedor(int nit, String nombre, String direccion, String nombreContacto, int telefonoContacto) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
    }
    public int getNit() {
        return nit;
    }
    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public int getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(int telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }


    

    

    
}
