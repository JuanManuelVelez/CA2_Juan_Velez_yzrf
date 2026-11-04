package mx.edu.utez.cursos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreParticipante;
    private Integer edadParticipante;
    private String correo;
    private LocalDate fechaInscripcion;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private InscripcionEstado estado;

    private Double calificacion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
}
