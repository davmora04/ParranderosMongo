package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import java.util.Date;
import java.util.List;

public class OrdenCompra {
    @Id
    private String id;
    private String proveedorId;  // ID del proveedor
    private String sucursalId;   // ID de la sucursal
    private Date fechaCreacion;  // Fecha de creación (automática)
    private Date fechaEntrega;   // Fecha esperada de entrega
    private String estado;       // Estado de la orden
    private List<DetalleOrdenCompra> detalles; // Detalles de los productos

    public OrdenCompra(String proveedorId, String sucursalId, Date fechaEntrega, List<DetalleOrdenCompra> detalles) {
        this.proveedorId = proveedorId;
        this.sucursalId = sucursalId;
        this.fechaCreacion = new Date();  // Fecha de creación actual
        this.fechaEntrega = fechaEntrega;
        this.detalles = detalles;
        this.estado = "vigente";  // El estado inicial es "vigente"
    }

    // Getters y setters
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }
}
