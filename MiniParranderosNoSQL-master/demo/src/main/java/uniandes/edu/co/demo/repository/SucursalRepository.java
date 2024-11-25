package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import uniandes.edu.co.demo.modelo.Sucursal;


public interface SucursalRepository extends MongoRepository<Sucursal, Integer> {

    //crear una nueva sucursal
    @Query("{ 'nombre' : ?1, 'instalacionEnM2' : ?2, 'direccion' : ?3, 'ciudad' : ?4, 'bodega' : ?5, 'ordenCompra' : ?6}") 
    void crearSucursal(String nombre, int instalacionEnM2, String direccion, List<String> ciudad, List<String> bodega, List<String> ordenCompra);

    
}
