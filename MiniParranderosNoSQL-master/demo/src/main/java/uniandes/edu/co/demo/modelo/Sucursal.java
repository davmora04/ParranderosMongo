package uniandes.edu.co.demo.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Sucursal {
    @Id 
    private int id;
    private String nombre;
    private int instalacionEnM2; 
    private String direccion;

    private List<Ciudad> ciudad; 
    private List<Bodega> bodega;
    private List<OrdenCompra> ordenCompra;

    public Sucursal(String nombre, int instalacionEnM2, String direccion) {
        this.nombre = nombre;
        this.instalacionEnM2 = instalacionEnM2;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getInstalacionEnM2() {
        return instalacionEnM2;
    }

    public void setInstalacionEnM2(int instalacionEnM2) {
        this.instalacionEnM2 = instalacionEnM2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Ciudad> getCiudad() {
        return ciudad;
    }

    public void setCiudad(List<Ciudad> ciudad) {
        this.ciudad = ciudad;
    }

    public List<Bodega> getBodega() {
        return bodega;
    }

    public void setBodega(List<Bodega> bodega) {
        this.bodega = bodega;
    }

    public List<OrdenCompra> getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(List<OrdenCompra> ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}

