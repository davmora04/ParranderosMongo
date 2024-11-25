package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.Producto;

public interface ProductoRepository extends MongoRepository<Producto, Integer> {
}
