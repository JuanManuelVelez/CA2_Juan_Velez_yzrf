package mx.edu.utez.cursos.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InscripcionRequest {
    @NotBlank(message = "El nombre del participante es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    private String nombreParticipante;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad debe ser mayor a cero")
    private Integer edadParticipante;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    private String correo;
}
