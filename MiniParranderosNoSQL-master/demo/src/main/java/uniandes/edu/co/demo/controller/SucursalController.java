package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Bodega;
import uniandes.edu.co.demo.modelo.Sucursal;
import uniandes.edu.co.demo.modelo.OrdenCompra;
import uniandes.edu.co.demo.repository.SucursalRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;

    // Crear nueva sucursal
    @PostMapping("/save/new")
    public ResponseEntity<?> saveSucursal(@RequestBody Sucursal sucursal) {
        try {
            sucursalRepository.save(sucursal);
            return new ResponseEntity<>("Sucursal creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la sucursal", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Crear una nueva bodega
    @PostMapping("/{sucursalId}/bodega")
    public ResponseEntity<?> agregarBodega(@PathVariable int sucursalId, @RequestBody Bodega bodega) {
        try {
            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
                List<Bodega> bodegas = sucursal.getBodega();
                bodegas.add(bodega);
                sucursal.setBodega(bodegas);
                sucursalRepository.save(sucursal);
                return new ResponseEntity<>("Bodega agregada exitosamente", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al agregar la bodega: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar una bodega
    @DeleteMapping("/{sucursalId}/bodega/{bodegaId}")
    public ResponseEntity<?> eliminarBodega(@PathVariable int sucursalId, @PathVariable int bodegaId) {
        try {
            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
                List<Bodega> bodegas = sucursal.getBodega();
                bodegas.removeIf(b -> b.getId() == bodegaId);
                sucursal.setBodega(bodegas);
                sucursalRepository.save(sucursal);
                return new ResponseEntity<>("Bodega eliminada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al eliminar la bodega: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todas las sucursales
    @GetMapping("")
    public ResponseEntity<List<Sucursal>> obtenerTodasLasSucursales() {
        try {
            List<Sucursal> sucursales = sucursalRepository.findAll();
            return ResponseEntity.ok(sucursales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una sucursal por ID
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerSucursalPorId(@PathVariable("id") int id) {
        try {
            Sucursal sucursal = sucursalRepository.findById(id).orElse(null);
            if (sucursal != null) {
                return ResponseEntity.ok(sucursal);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar una sucursal por ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarSucursal(@PathVariable("id") int id) {
        try {
            sucursalRepository.deleteById(id);
            return new ResponseEntity<>("Sucursal eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la sucursal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   

    @PostMapping("/{sucursalId}/ordencompra/save")
    public ResponseEntity<String> crearOrdenCompra(@PathVariable String sucursalId, @RequestBody OrdenCompra ordenCompraRequest) {
        try {
            // Buscar la sucursal por ID
            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(Integer.parseInt(sucursalId));
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
    
                // Crear la nueva orden de compra con los detalles del request
                OrdenCompra ordenCompra = new OrdenCompra(
                    ordenCompraRequest.getProveedorId(),
                    ordenCompraRequest.getSucursalId(),
                    ordenCompraRequest.getFechaEntrega(),
                    ordenCompraRequest.getDetalles()
                );
    
                // Asignar el ID manualmente (this ID should be provided in the request body)
                if (ordenCompraRequest.getId() != null) {
                    ordenCompra.setId(ordenCompraRequest.getId()); // Ensure the ID is set from the request
                }
    
                // Añadir la orden de compra a la sucursal
                List<OrdenCompra> ordenes = sucursal.getOrdenCompra();
                ordenes.add(ordenCompra);
                sucursal.setOrdenCompra(ordenes);
    
                // Guardar la sucursal con la orden de compra
                sucursalRepository.save(sucursal);
                System.out.println("Saving order with ID: " + ordenCompra.getId());

                return new ResponseEntity<>("Orden de compra creada exitosamente", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la orden de compra: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

// Obtener una orden de compra de una sucursal por ID
@GetMapping("/{sucursalId}/ordencompra/{ordenCompraId}")
public ResponseEntity<OrdenCompra> obtenerOrdenCompraPorId(@PathVariable("sucursalId") String sucursalId,
                                                            @PathVariable("ordenCompraId") String ordenCompraId) {
    try {
        // Buscar la sucursal por ID
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(Integer.parseInt(sucursalId));
        if (sucursalOptional.isPresent()) {
            Sucursal sucursal = sucursalOptional.get();

            // Buscar la orden de compra dentro de las órdenes de la sucursal
            List<OrdenCompra> ordenes = sucursal.getOrdenCompra();
            for (OrdenCompra orden : ordenes) {
                if (orden.getId().equals(ordenCompraId)) {
                    // Si se encuentra la orden de compra con el ID, devolverla
                    return ResponseEntity.ok(orden);
                }
            }

            // Si no se encuentra la orden de compra con ese ID, devolver un error 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Orden de compra no encontrada
        } else {
            // Si no se encuentra la sucursal con ese ID, devolver un error 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Sucursal no encontrada
        }
    } catch (Exception e) {
        // Si hay un error al hacer la consulta, devolver un error 500
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error en la consulta
    }
}




    
}
