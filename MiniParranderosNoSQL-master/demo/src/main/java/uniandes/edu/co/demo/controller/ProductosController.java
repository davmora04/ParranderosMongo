package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.repository.ProductoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear un nuevo producto
    @PostMapping("/new/save")
    public ResponseEntity<String> crearProducto(@RequestBody Producto producto) {
        try {
            productoRepository.save(producto);
            return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el producto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los productos
    @GetMapping("")
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            // Registrar el error para diagnóstico
            e.printStackTrace(); // Esto te dará más información sobre el problema en la consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // RFC2 - Filter products based on criteria (price, expiration, category)
@PostMapping("/productos/filtrar")
public ResponseEntity<?> filtrarProductos(@RequestBody Map<String, Object> body) {

    // Parse optional fields from JSON body
    Double minPrecio = body.containsKey("minPrecio") ? ((Number) body.get("minPrecio")).doubleValue() : null;
    Double maxPrecio = body.containsKey("maxPrecio") ? ((Number) body.get("maxPrecio")).doubleValue() : null;
    String expirationDateStr = (String) body.get("expirationDateStr");
    Integer idCategoria = body.containsKey("idCategoria") ? (Integer) body.get("idCategoria") : null;
    String sucursalId = (String) body.get("idSucursal");

    LocalDate expirationDate = null;

    // Parse expiration date if provided
    if (expirationDateStr != null) {
        try {
            expirationDate = LocalDate.parse(expirationDateStr);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Fecha de expiración inválida", HttpStatus.BAD_REQUEST);
        }
    }

    // Fetch the products matching the criteria from MongoDB
    List<Producto> productos = productoRepository.buscarProductosPorCriterios(
            minPrecio, maxPrecio, expirationDate, idCategoria, sucursalId);

    if (productos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(productos, HttpStatus.OK);
}



    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable("id") String id) {
        try {
            int codigoBarras = Integer.parseInt(id);  // Convertir String a int
            Producto producto = productoRepository.findById(codigoBarras).orElse(null);
            if (producto != null) {
                return ResponseEntity.ok(producto);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Error si el id no es un número válido
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") String id) {
        try {
            int codigoBarras = Integer.parseInt(id);  // Convertir String a int
            productoRepository.deleteById(codigoBarras);
            return new ResponseEntity<>("Producto eliminado exitosamente", HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID inválido: debe ser un número", HttpStatus.BAD_REQUEST); // Manejar el caso de id no válido
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el producto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
