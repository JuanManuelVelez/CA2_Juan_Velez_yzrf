package mx.edu.utez.cursos.service;

import mx.edu.utez.cursos.controller.dto.InscripcionRequest;
import mx.edu.utez.cursos.exception.CustomException;
import mx.edu.utez.cursos.model.Curso;
import mx.edu.utez.cursos.model.CursoEstado;
import mx.edu.utez.cursos.model.Inscripcion;
import mx.edu.utez.cursos.model.InscripcionEstado;
import mx.edu.utez.cursos.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class InscripcionService {

    final InscripcionRepository inscripcionRepository;
    final CursoService cursoService;

    public InscripcionService(InscripcionRepository inscripcionRepository, CursoService cursoService) {
        this.inscripcionRepository = inscripcionRepository;
        this.cursoService = cursoService;
    }

    public Inscripcion inscribir(Long cursoId, InscripcionRequest request) {
        Curso curso = cursoService.obtener(cursoId);

        if (curso.getEstado() != CursoEstado.DISPONIBLE) {
            throw new CustomException("Solo es posible inscribirse en cursos DISPONIBLES.");
        }
        if (!LocalDate.now().isBefore(curso.getFechaInicio())) {
            throw new CustomException("El periodo de inscripción terminó porque el curso ya comenzó.");
        }

        int ocupados = 0;
        for (Inscripcion inscripcionActual : inscripcionRepository.findByCursoId(cursoId)) {
            if (inscripcionActual.getEstado() == InscripcionEstado.ACTIVA
                    || inscripcionActual.getEstado() == InscripcionEstado.COMPLETADA) {
                ocupados++;
            }
        }
        if (ocupados >= curso.getCupoMaximo()) {
            throw new CustomException("El curso ya alcanzó su cupo máximo.");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setNombreParticipante(request.getNombreParticipante().trim());
        inscripcion.setEdadParticipante(request.getEdadParticipante());
        inscripcion.setCorreo(request.getCorreo().trim().toLowerCase());
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado(InscripcionEstado.ACTIVA);
        inscripcion.setCalificacion(null);
        inscripcion.setCurso(curso);

        return inscripcionRepository.save(inscripcion);
    }

    public Inscripcion cancelar(Long id) {
        Inscripcion inscripcion = obtener(id);

        if (inscripcion.getEstado() != InscripcionEstado.ACTIVA) {
            throw new CustomException("Solo se pueden cancelar inscripciones ACTIVAS.");
        }
        if (inscripcion.getCurso().getEstado() != CursoEstado.DISPONIBLE
                || !LocalDate.now().isBefore(inscripcion.getCurso().getFechaInicio())) {
            throw new CustomException("No se puede cancelar: el curso ya comenzó.");
        }

        inscripcion.setEstado(InscripcionEstado.CANCELADA);
        return inscripcionRepository.save(inscripcion);
    }

    public Inscripcion registrarCalificacion(Long id, Double calificacion) {
        Inscripcion inscripcion = obtener(id);

        if (calificacion == null || calificacion < 0 || calificacion > 10) {
            throw new CustomException("La calificación debe estar entre 0 y 10.");
        }
        if (inscripcion.getCurso().getEstado() != CursoEstado.FINALIZADO) {
            throw new CustomException("Solo se puede calificar cuando el curso está FINALIZADO.");
        }
        if (inscripcion.getEstado() != InscripcionEstado.ACTIVA) {
            throw new CustomException("Solo se pueden calificar inscripciones ACTIVAS.");
        }

        inscripcion.setCalificacion(calificacion);
        inscripcion.setEstado(InscripcionEstado.COMPLETADA);
        return inscripcionRepository.save(inscripcion);
    }

    public Inscripcion obtener(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new CustomException("No existe la inscripción con ID " + id));
    }
}
