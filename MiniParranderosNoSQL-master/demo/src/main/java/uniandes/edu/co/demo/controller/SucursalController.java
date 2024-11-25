package uniandes.edu.co.demo.controller;

import java.util.List;
import java.util.Optional;

//import java.util.List;

//import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Bodega;
import uniandes.edu.co.demo.modelo.Sucursal;
import uniandes.edu.co.demo.repository.SucursalRepository;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;

    // crear nueva sucursal
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
            // Buscar la sucursal
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
}



