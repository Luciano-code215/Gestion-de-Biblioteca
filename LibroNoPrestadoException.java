
/**
 * Excepción que se lanza cuando se intenta realizar una operación
 * sobre un libro que no se encuentra prestado.
 *
 * Se utiliza, por ejemplo, al intentar devolver un libro que
 * ya está disponible en la biblioteca o al consultar un préstamo
 * inexistente.
 *
 * @author Luciano Pedotti
 * @version 24/12/2025
 */
public class LibroNoPrestadoException extends Exception
{
    public LibroNoPrestadoException(){
        super("**El libro esta en biblioteca**");
    }
}