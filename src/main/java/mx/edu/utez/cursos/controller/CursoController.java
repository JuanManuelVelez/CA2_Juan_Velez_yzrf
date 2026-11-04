package mx.edu.utez.cursos.controller;

import jakarta.validation.Valid;
import mx.edu.utez.cursos.controller.dto.InscripcionRequest;
import mx.edu.utez.cursos.model.Inscripcion;
import mx.edu.utez.cursos.service.InscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    final InscripcionService inscripcionService;

    public CursoController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PostMapping("/{id}/inscripciones")
    public ResponseEntity<Inscripcion> inscribir(@PathVariable Long id, @RequestBody @Valid InscripcionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionService.inscribir(id, request));
    }
}
