/**
 * Excepción que se lanza cuando se intenta prestar un libro
 * que ya se encuentra actualmente prestado.
 *
 * Se utiliza para evitar que un mismo libro tenga
 * más de un préstamo activo al mismo tiempo.
 *
 * @author Luciano Pedotti
 * @version 24/12/2025
 */

public class LibroPrestadoException extends Exception
{
    public LibroPrestadoException(){
        super("**El libro se encuentra prestado**");
    }
}