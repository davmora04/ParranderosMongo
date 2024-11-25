package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.Proveedor;

public interface ProveedorRepository extends MongoRepository<Proveedor, Integer> {

    //crear un nuevo proveedor
    @Query("{ 'nombre' : ?1, 'direccion' : ?2, 'nombreContacto' : ?3, 'telefonoContacto' : ?4}")
    void crearProveedor(String nombre, String direccion, String nombreContacto, int telefonoContacto);

    //update proveedor por nit 
    @Query("{ 'nit' : ?0 }")
    @Update("{$set: { 'nombre' : ?1, 'direccion' : ?2, 'nombreContacto' : ?3, 'telefonoContacto' : ?4}}")
    void updateProveedorPorNit(int nit, String nombre, String direccion, String nombreContacto, String telefonoContacto);


    
}
