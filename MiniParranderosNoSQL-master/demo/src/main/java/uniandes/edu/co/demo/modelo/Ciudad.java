package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;

public class Ciudad {
    @Id
    private long codigo;
    private String nombre; 
    

    public Ciudad(String nombre, long codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    

    
}
