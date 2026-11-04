package mx.edu.utez.cursos.exception;

public class CustomException extends RuntimeException {
    public CustomException(String mensaje) {
        super(mensaje);
    }
}
