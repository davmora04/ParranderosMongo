package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.modelo.Producto;

public interface ProductoRepository extends MongoRepository<Producto, Integer> {

    // Buscar una categoría dentro de los productos usando el código
    @Query("{ 'categoria.codigo': ?0 }")
    List<Producto> findProductosByCategoriaCodigo(int codigo);

    // Buscar una categoría dentro de los productos usando el nombre
    @Query("{ 'categoria.nombre': ?0 }")
    List<Producto> findProductosByCategoriaNombre(String nombre);


}
