package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.OrdenCompra;
import java.util.Optional;

public interface OrdenCompraRepository extends MongoRepository<OrdenCompra, String> {
    // This will allow querying by the "id" of the order, which is a String and will return an Optional
    Optional<OrdenCompra> findById(String id);
}
