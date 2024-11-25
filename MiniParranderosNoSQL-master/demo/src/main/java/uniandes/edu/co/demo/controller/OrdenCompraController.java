package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.OrdenCompra;
import uniandes.edu.co.demo.modelo.DetalleOrdenCompra;
import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.repository.OrdenCompraRepository;

import java.util.List;

@RestController
@RequestMapping("/ordencompra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @PostMapping("/new/save")
    public ResponseEntity<String> crearOrdenCompra(@RequestBody OrdenCompra ordenCompra) {
        try {
            // Aseguramos que la orden de compra está en estado 'vigente'
            ordenCompra.setEstado("vigente");
            ordenCompraRepository.save(ordenCompra);
            return new ResponseEntity<>("Orden de compra creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la orden de compra: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Método para obtener una orden de compra por ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<OrdenCompra> obtenerOrdenCompraPorId(@PathVariable("id") String id) {
        try {
            OrdenCompra ordenCompra = ordenCompraRepository.findById(id).orElse(null);
            if (ordenCompra != null) {
                return ResponseEntity.ok(ordenCompra);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
