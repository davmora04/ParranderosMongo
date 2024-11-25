package uniandes.edu.co.demo.modelo;

public class DetalleOrdenCompra {
    private Producto producto;
    private int cantidad;
    private double precioAcordado;

    public DetalleOrdenCompra(Producto producto, int cantidad, double precioAcordado) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioAcordado = precioAcordado;
    }

    // Getters y setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioAcordado() {
        return precioAcordado;
    }

    public void setPrecioAcordado(double precioAcordado) {
        this.precioAcordado = precioAcordado;
    }
}
