package mx.edu.utez.cursos.service;

import mx.edu.utez.cursos.exception.CustomException;
import mx.edu.utez.cursos.model.Curso;
import mx.edu.utez.cursos.repository.CursoRepository;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso obtener(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CustomException("No existe el curso con ID " + id));
    }
}
