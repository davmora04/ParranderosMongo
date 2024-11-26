package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.time.LocalDate;

public class OrdenCompra {

    @Id
    private String id;  // Unique order ID
    private String proveedorId;
    private String sucursalId;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private String estado;
    private List<DetalleOrdenCompra> detalles;  // Embed product details here

    // Constructor and getters/setters
    public OrdenCompra(String proveedorId, String sucursalId, LocalDate fechaEntrega, List<DetalleOrdenCompra> detalles) {
        this.proveedorId = proveedorId;
        this.sucursalId = sucursalId;
        this.fechaCreacion = LocalDate.now();
        this.fechaEntrega = fechaEntrega;
        this.estado = "vigente";
        this.detalles = detalles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }
}
