package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.OrdenCompra;
import uniandes.edu.co.demo.modelo.Sucursal;
import uniandes.edu.co.demo.repository.OrdenCompraRepository;
import uniandes.edu.co.demo.repository.SucursalRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordencompra")
public class OrdenCompraController {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    // Crear una nueva orden de compra dentro de una sucursal
    @PostMapping("/{sucursalId}/save")
    public ResponseEntity<String> crearOrdenCompra(@PathVariable String sucursalId, @RequestBody OrdenCompra ordenCompraRequest) {
        try {
            // Buscar la sucursal por ID
            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(Integer.parseInt(sucursalId));
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();

                // Crear la nueva orden de compra
                OrdenCompra ordenCompra = new OrdenCompra(
                    ordenCompraRequest.getProveedorId(),
                    ordenCompraRequest.getSucursalId(),
                    ordenCompraRequest.getFechaEntrega(),
                    ordenCompraRequest.getDetalles()
                );

                // Asignar el ID manualmente
                ordenCompra.setId(ordenCompraRequest.getId());

                // Añadir la orden de compra a la sucursal
                List<OrdenCompra> ordenes = sucursal.getOrdenCompra();
                ordenes.add(ordenCompra);
                sucursal.setOrdenCompra(ordenes);

                // Guardar la sucursal con la nueva orden de compra
                sucursalRepository.save(sucursal);

                // Guardar la orden de compra en la base de datos
                ordenCompraRepository.save(ordenCompra);

                return new ResponseEntity<>("Orden de compra creada exitosamente", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la orden de compra: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener una orden de compra por ID
    @GetMapping("/ordencompra/{ordenCompraId}")
    public ResponseEntity<OrdenCompra> obtenerOrdenCompraPorId(
            @PathVariable("ordenCompraId") String ordenCompraId) {
        try {
            // Define the query to find OrdenCompra by ordenCompraId
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(ordenCompraId));

            // Execute the query
            OrdenCompra ordenCompra = mongoTemplate.findOne(query, OrdenCompra.class);

            // If found, return the OrdenCompra, otherwise return NOT_FOUND
            if (ordenCompra != null) {
                return ResponseEntity.ok(ordenCompra);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exception gracefully
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
