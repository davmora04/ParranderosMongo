package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.OrdenCompra;

public interface OrdenCompraRepository extends MongoRepository<OrdenCompra, String> {
}
