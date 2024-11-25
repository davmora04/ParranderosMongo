package uniandes.edu.co.demo.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Bodega {
    @Id
    private int id;
    private String nombre; 
    private int tamanioEnM2; 
    private List<RecepcionProducto> RecepcionProducto;

    public Bodega () {

    }
    
    public Bodega(String nombre, int tamanioEnM2) {
        this.nombre = nombre;
        this.tamanioEnM2 = tamanioEnM2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamanioEnM2() {
        return tamanioEnM2;
    }

    public void setTamanioEnM2(int tamanioEnM2) {
        this.tamanioEnM2 = tamanioEnM2;
    }

    public List<RecepcionProducto> getRecepcionProducto() {
        return RecepcionProducto;
    }

    public void setRecepcionProducto(List<RecepcionProducto> recepcionProducto) {
        RecepcionProducto = recepcionProducto;
    } 
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    
    

    
}
