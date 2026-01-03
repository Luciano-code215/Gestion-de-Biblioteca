/**
 * Excepción que se lanza cuando se intenta operar sobre un socio
 * que no existe en la biblioteca.
 *
 * Se utiliza, por ejemplo, al buscar, quitar o realizar operaciones
 * sobre un socio identificado por un DNI inexistente.
 *
 * @author Luciano Pedotti
 * @version 24/12/2025
 */

public class SocioNoExistenteException extends Exception
{
    public SocioNoExistenteException (){
        super("**El socio no existe**");
    }
}