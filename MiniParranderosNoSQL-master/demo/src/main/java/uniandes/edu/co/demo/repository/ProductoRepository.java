package uniandes.edu.co.demo.repository;

import java.time.LocalDate;
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

    // MongoDB query to filter products based on multiple criteria
    @Query("{ 'precioVenta' : { $gte: ?0, $lte: ?1 }, 'fechaVencimiento' : { $lt: ?2 }, 'categoria.id' : ?3, 'sucursalId' : ?4 }")
    List<Producto> buscarProductosPorCriterios(Double minPrecio, Double maxPrecio, LocalDate fechaVencimiento, Integer idCategoria, String sucursalId);


 // RFC1 - Buscar productos por características
 @Query("{ 'precioVenta': { $gte: ?0, $lte: ?1 }, 'fechaVencimiento': { $gte: ?2, $lte: ?3 }, 'categoria.codigo': ?4 }")
 List<Producto> findByCaracteristicas(int precioMin, int precioMax, LocalDate fechaExpInf, LocalDate fechaExpSup, int categoriaCodigo);
}

