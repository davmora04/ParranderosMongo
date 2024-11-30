package uniandes.edu.co.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Bodega;
import uniandes.edu.co.demo.modelo.DetalleOrdenCompra;
import uniandes.edu.co.demo.modelo.OrdenCompra;
import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.modelo.RecepcionProducto;
import uniandes.edu.co.demo.modelo.Sucursal;
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
            return new ResponseEntity<>("Error al eliminar la bodega: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener inventario de productos en una sucursal
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
                                (double) recepcionProducto.getCostoUnitario()
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
            return new ResponseEntity<>("Error al agregar el producto a la bodega: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    // Crear una orden de compra
    @PostMapping("/{sucursalId}/ordencompra/save")
    public ResponseEntity<String> crearOrdenCompra(@PathVariable int sucursalId, @RequestBody OrdenCompra ordenCompraRequest) {
        try {
            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
                List<DetalleOrdenCompra> detalles = ordenCompraRequest.getDetalles();

                OrdenCompra ordenCompra = new OrdenCompra(
                    ordenCompraRequest.getProveedorId(),
                    ordenCompraRequest.getSucursalId(),
                    ordenCompraRequest.getFechaEntrega(),
                    detalles
                );

                if (ordenCompraRequest.getId() != null) {
                    ordenCompra.setId(ordenCompraRequest.getId());
                }

                List<OrdenCompra> ordenes = sucursal.getOrdenCompra();
                ordenes.add(ordenCompra);
                sucursal.setOrdenCompra(ordenes);

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
    public ResponseEntity<OrdenCompra> obtenerOrdenCompraPorId(@PathVariable("sucursalId") int sucursalId,
                                                                @PathVariable("ordenCompraId") String ordenCompraId) {
        try {
            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
                List<OrdenCompra> ordenes = sucursal.getOrdenCompra();

                for (OrdenCompra orden : ordenes) {
                    if (orden.getId().equals(ordenCompraId)) {
                        return ResponseEntity.ok(orden);
                    }
                }

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Filtrar productos por características
    @PostMapping("/{sucursalId}/productos/filtrar")
    public ResponseEntity<List<Producto>> filtrarProductosPorCaracteristicas(
        @PathVariable int sucursalId, 
        @RequestParam(value = "precioMin", required = false) Integer precioMin, 
        @RequestParam(value = "precioMax", required = false) Integer precioMax, 
        @RequestParam(value = "fechaVencimiento", required = false) String fechaVencimientoStr,
        @RequestParam(value = "categoria", required = false) String categoria) {
        try {
            LocalDate fechaVencimiento = (fechaVencimientoStr != null) ? LocalDate.parse(fechaVencimientoStr) : null;

            Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
            if (sucursalOptional.isPresent()) {
                Sucursal sucursal = sucursalOptional.get();
                List<Producto> productosFiltrados = new ArrayList<>();

                for (OrdenCompra ordenCompra : sucursal.getOrdenCompra()) {
                    for (DetalleOrdenCompra detalle : ordenCompra.getDetalles()) {
                        Producto producto = detalle.getProducto();
                        boolean match = true;

                        if (precioMin != null && producto.getPrecioVenta() < precioMin) {
                            match = false;
                        }
                        if (precioMax != null && producto.getPrecioVenta() > precioMax) {
                            match = false;
                        }
                        if (fechaVencimiento != null && producto.getFechaVencimiento().isBefore(fechaVencimiento)) {
                            match = false;
                        }
                        if (categoria != null && !producto.getCategoria().stream()
                            .anyMatch(c -> c.getNombre().equalsIgnoreCase(categoria))) {
                            match = false;
                        }

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

    // Obtener productos de una sucursal
    @GetMapping("/{sucursalId}/productos")
    public ResponseEntity<?> obtenerProductos(@PathVariable int sucursalId,
                                              @RequestParam(required = false) Double precioMin,
                                              @RequestParam(required = false) Double precioMax,
                                              @RequestParam(required = false) String fechaVencimiento,
                                              @RequestParam(required = false) String categoria) {
        try {
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
                        boolean precioValido = (precioMin == null || producto.getPrecioVenta() >= precioMin) &&
                                               (precioMax == null || producto.getPrecioVenta() <= precioMax);
                        boolean fechaValida = (fechaVencimiento == null || 
                                               producto.getFechaVencimiento().isBefore(LocalDate.parse(fechaVencimiento)));
                        boolean categoriaValida = (categoria == null || 
                                                   (producto.getCategoria() != null && producto.getCategoria().stream()
                                                            .anyMatch(c -> c.getNombre().equalsIgnoreCase(categoria))));

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
