package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Bodega;
import uniandes.edu.co.demo.modelo.DetalleOrdenCompra;
import uniandes.edu.co.demo.modelo.Sucursal;
import uniandes.edu.co.demo.modelo.OrdenCompra;
import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.repository.SucursalRepository;

import java.time.LocalDate;
import java.util.ArrayList;
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
            
            // Set up the detalles (embedded products)
            List<DetalleOrdenCompra> detalles = ordenCompraRequest.getDetalles();

            // Create the new order with the provided details
            OrdenCompra ordenCompra = new OrdenCompra(
                ordenCompraRequest.getProveedorId(),
                ordenCompraRequest.getSucursalId(),
                ordenCompraRequest.getFechaEntrega(),
                detalles
            );

            // Ensure ID is set manually
            if (ordenCompraRequest.getId() != null) {
                ordenCompra.setId(ordenCompraRequest.getId());  // Ensure the ID is set from the request
            }

            // Add the order to the Sucursal
            List<OrdenCompra> ordenes = sucursal.getOrdenCompra();
            ordenes.add(ordenCompra);
            sucursal.setOrdenCompra(ordenes);

            // Save the updated Sucursal with the embedded OrdenCompra
            sucursalRepository.save(sucursal);

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

// In SucursalController

@PostMapping("/{sucursalId}/productos/filtrar")
public ResponseEntity<List<Producto>> filtrarProductosPorCaracteristicas(
    @PathVariable String sucursalId, 
    @RequestParam(value = "precioMin", required = false) Integer precioMin, 
    @RequestParam(value = "precioMax", required = false) Integer precioMax, 
    @RequestParam(value = "fechaVencimiento", required = false) String fechaVencimientoStr,
    @RequestParam(value = "categoria", required = false) String categoria) {

    try {
        // Parse fechaVencimiento if provided
        LocalDate fechaVencimiento = null;
        if (fechaVencimientoStr != null) {
            fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
        }

        // Find sucursal by ID
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(Integer.parseInt(sucursalId));
        if (sucursalOptional.isPresent()) {
            Sucursal sucursal = sucursalOptional.get();

            // Filter productos through OrdenCompra details
            List<Producto> productosFiltrados = new ArrayList<>();
            for (OrdenCompra ordenCompra : sucursal.getOrdenCompra()) {
                for (DetalleOrdenCompra detalle : ordenCompra.getDetalles()) {
                    Producto producto = detalle.getProducto();

                    boolean match = true;

                    // Check price range
                    if (precioMin != null && producto.getPrecioVenta() < precioMin) {
                        match = false;
                    }
                    if (precioMax != null && producto.getPrecioVenta() > precioMax) {
                        match = false;
                    }

                    // Check expiration date
                    if (fechaVencimiento != null && producto.getFechaVencimiento().isBefore(fechaVencimiento)) {
                        match = false;
                    }

                    // Check category
                    if (categoria != null && !producto.getCategoria().stream()
                            .anyMatch(c -> c.getNombre().equalsIgnoreCase(categoria))) {
                        match = false;
                    }

                    // If all conditions match, add the product to the list
                    if (match) {
                        productosFiltrados.add(producto);
                    }
                }
            }

            return ResponseEntity.ok(productosFiltrados);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
    }
}

@GetMapping("/{sucursalId}/productos")
public ResponseEntity<?> obtenerProductos(@PathVariable int sucursalId,
                                          @RequestParam(required = false) Double precioMin,
                                          @RequestParam(required = false) Double precioMax,
                                          @RequestParam(required = false) String fechaVencimiento,
                                          @RequestParam(required = false) String categoria) {
    try {
        // Retrieve the Sucursal by its ID
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
        if (!sucursalOptional.isPresent()) {
            return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.NOT_FOUND);
        }

        Sucursal sucursal = sucursalOptional.get();
        List<OrdenCompra> ordenesCompra = sucursal.getOrdenCompra();

        List<Producto> productosFiltrados = new ArrayList<>();

        for (OrdenCompra orden : ordenesCompra) {
            for (DetalleOrdenCompra detalle : orden.getDetalles()) {
                Producto producto = detalle.getProducto();
                if (producto != null) {
                    // Filter by price
                    boolean precioValido = (precioMin == null || producto.getPrecioVenta() >= precioMin) &&
                                           (precioMax == null || producto.getPrecioVenta() <= precioMax);

                    // Filter by expiration date
                    boolean fechaValida = (fechaVencimiento == null || 
                                           producto.getFechaVencimiento().isBefore(LocalDate.parse(fechaVencimiento)));

                    // Filter by category, ensure categoria is not null before calling stream()
                    boolean categoriaValida = (categoria == null || 
                                               (producto.getCategoria() != null && producto.getCategoria().stream()
                                                        .anyMatch(c -> c.getNombre().equalsIgnoreCase(categoria))));

                    // If all conditions are met, add to the filtered list
                    if (precioValido && fechaValida && categoriaValida) {
                        productosFiltrados.add(producto);
                    }
                }
            }
        }

        return new ResponseEntity<>(productosFiltrados, HttpStatus.OK);

    } catch (Exception e) {
        return new ResponseEntity<>("Error al obtener productos", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




    
}
