package uniandes.edu.co.demo.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Bar;
import uniandes.edu.co.demo.repository.BarRepository;
import uniandes.edu.co.demo.repository.BarRepositoryCustom;

@RestController
@RequestMapping("/bares")
public class BaresController {

    @Autowired
    private BarRepository barRepository;

    // Crear un nuevo bar
    @PostMapping("/new/save")
    public ResponseEntity<String> crearBar(@RequestBody Bar bar) {
        try {
            barRepository.save(bar);
            return new ResponseEntity<>("Bar creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {;
            return new ResponseEntity<>("Error al crear el bar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un bar existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarBar(@PathVariable("id") int id, @RequestBody Bar bar) {
        try {
            barRepository.actualizarBar(
                id,
                bar.getNombre(),
                bar.getCiudad(),
                bar.getPresupuesto(),
                bar.getCant_sedes(),
                bar.getOferta_bebidas()
            );
            return new ResponseEntity<>("Bar actualizado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el bar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los bares
    @GetMapping("")
    public ResponseEntity<List<Bar>> obtenerTodosLosBares() {
        try {
            List<Bar> bares = barRepository.buscarTodosLosBares();
            return ResponseEntity.ok(bares);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un bar por ID
    @GetMapping("/{id}")
    public ResponseEntity<List<Bar>> obtenerBarPorId(@PathVariable("id") int id) {
        try {
            List<Bar> bares = barRepository.buscarPorId(id);
            if (bares != null && !bares.isEmpty()) {
                return ResponseEntity.ok(bares);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar un bar por su ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarBar(@PathVariable("id") int id) {
        try {
            barRepository.eliminarBarPorId(id);
            return new ResponseEntity<>("Bar eliminado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el bar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private BarRepositoryCustom barRepositoryCustom;

    // Obtener las bebidas más consumidas
    @GetMapping("/mas-consumidas")
    public ResponseEntity<List<Document>> obtenerBebidasMasConsumidas() {
        try {
            // Llamar al método en el repository que realiza la agregación
            List<Document> resultado = barRepositoryCustom.obtenerBebidasMasConsumidas();

            // Retornar el resultado de la consulta
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}