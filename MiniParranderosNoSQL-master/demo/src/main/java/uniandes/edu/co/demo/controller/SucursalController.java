package uniandes.edu.co.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Bodega;
import uniandes.edu.co.demo.modelo.Sucursal;
import uniandes.edu.co.demo.modelo.RecepcionProducto;
import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.repository.SucursalRepository;

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

    // Obtener inventario de productos en una sucursal (RFC2)
    @GetMapping("/{sucursalId}/inventario")
    public ResponseEntity<?> obtenerInventarioPorSucursal(@PathVariable int sucursalId) {
        try {
            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
                List<Bodega> bodegas = sucursal.getBodega();

                List<String> reporteInventario = new ArrayList<>();

                for (Bodega bodega : bodegas) {
                    for (RecepcionProducto recepcionProducto : bodega.getRecepcionProducto()) {
                        Producto producto = recepcionProducto.getProducto();
                        if (producto != null) {
                            String reporte = String.format(
                                "Bodega: %s, Producto: %s, Cantidad Actual: %d, Cantidad Mínima: %d, Costo Promedio: %.2f",
                                bodega.getNombre(),
                                producto.getNombre(),
                                recepcionProducto.getCantidadRecibida(),
                                recepcionProducto.getCantidadMinima(),
                                (double) recepcionProducto.getCostoUnitario() // Suponiendo que el costo promedio sea el costo unitario
                            );
                            reporteInventario.add(reporte);
                        }
                    }
                }

                return ResponseEntity.ok(reporteInventario);
            } else {
                return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al obtener el inventario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Añadir Recepción de Producto a una Bodega
    @PostMapping("/{sucursalId}/bodega/{bodegaId}/recepcion")
    public ResponseEntity<?> agregarRecepcionProducto(
        @PathVariable int sucursalId, 
        @PathVariable int bodegaId, 
        @RequestBody RecepcionProducto recepcionProducto) {
        try {
            if (recepcionProducto.getProducto() == null) {
                return new ResponseEntity<>("El producto no puede ser nulo", HttpStatus.BAD_REQUEST);
            }

            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
                List<Bodega> bodegas = sucursal.getBodega();
                for (Bodega bodega : bodegas) {
                    if (bodega.getId() == bodegaId) {
                        // Agregar el producto a la bodega
                        List<RecepcionProducto> productos = bodega.getRecepcionProducto();
                        productos.add(recepcionProducto);
                        bodega.setRecepcionProducto(productos);
                        sucursalRepository.save(sucursal);
                        return new ResponseEntity<>("Producto agregado exitosamente a la bodega", HttpStatus.CREATED);
                    }
                }
                return new ResponseEntity<>("Bodega no encontrada", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al agregar el producto a la bodega: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
