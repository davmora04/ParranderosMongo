package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.CategoriaProducto;
import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.repository.ProductoRepository;

import java.util.List;

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
    // Crear una categoría para un producto
    @PostMapping("/{id}/categoria")
    public ResponseEntity<String> agregarCategoriaAProducto(@PathVariable("id") int id, @RequestBody CategoriaProducto categoria) {
        try {
            Producto producto = productoRepository.findById(id).orElse(null);
            if (producto == null) {
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            }

            // Agregar la categoría al producto
            List<CategoriaProducto> categorias = producto.getCategoria();
            categorias.add(categoria);
            producto.setCategoria(categorias);

            // Guardar el producto actualizado
            productoRepository.save(producto);

            return new ResponseEntity<>("Categoría agregada al producto exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar la categoría: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar categorías por código
    @GetMapping("/categoria/codigo/{codigo}")
    public ResponseEntity<List<Producto>> obtenerProductosPorCategoriaCodigo(@PathVariable("codigo") int codigo) {
        try {
            List<Producto> productos = productoRepository.findProductosByCategoriaCodigo(codigo);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar categorías por nombre
    @GetMapping("/categoria/nombre/{nombre}")
    public ResponseEntity<List<Producto>> obtenerProductosPorCategoriaNombre(@PathVariable("nombre") String nombre) {
        try {
            List<Producto> productos = productoRepository.findProductosByCategoriaNombre(nombre);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
