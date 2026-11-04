package mx.edu.utez.cursos.repository;

import mx.edu.utez.cursos.model.Inscripcion;
import mx.edu.utez.cursos.model.InscripcionEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByCursoId(Long cursoId);

}
