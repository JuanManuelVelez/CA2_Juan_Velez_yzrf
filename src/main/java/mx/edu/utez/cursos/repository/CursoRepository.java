package mx.edu.utez.cursos.repository;

import mx.edu.utez.cursos.model.Curso;
import mx.edu.utez.cursos.model.CursoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
