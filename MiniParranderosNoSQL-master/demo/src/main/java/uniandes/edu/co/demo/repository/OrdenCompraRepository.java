package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.OrdenCompra;
import java.util.Optional;

public interface OrdenCompraRepository extends MongoRepository<OrdenCompra, String> {

    // Custom query to find an order by its id and sucursalId
    Optional<OrdenCompra> findByIdAndSucursalId(String id, String sucursalId);
}
