/**
 * Excepción que se lanza cuando se intenta acceder o operar
 * sobre un libro que no existe en la biblioteca.
 *
 * Se utiliza, por ejemplo, al seleccionar un libro inexistente
 * desde la consola o al buscar un libro fuera del rango válido.
 *
 * @author Luciano Pedotti
 * @version 24/12/2025
 */
public class LibroNoExistenteException extends Exception
{
    LibroNoExistenteException(){
        super("**LIbro no existente**");
    }
}