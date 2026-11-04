package mx.edu.utez.cursos.controller;

import jakarta.validation.Valid;
import mx.edu.utez.cursos.controller.dto.CalificacionRequest;
import mx.edu.utez.cursos.model.Inscripcion;
import mx.edu.utez.cursos.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Inscripcion> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.cancelar(id));
    }

    @PatchMapping("/{id}/calificacion")
    public ResponseEntity<Inscripcion> calificar(@PathVariable Long id, @RequestBody @Valid CalificacionRequest request) {
        return ResponseEntity.ok(inscripcionService.registrarCalificacion(id, request.getCalificacion()));
    }
}
