
/**
 * Excepción que se lanza cuando se intenta registrar un socio
 * con un DNI que ya existe en la biblioteca.
 *
 * Se utiliza al crear un nuevo socio (estudiante o docente)
 * para evitar duplicados dentro del sistema.
 *
 * @author Luciano Pedotti
 * @version 24/12/2025
 */

public class SocioDuplicadoException extends Exception
{
    public SocioDuplicadoException (){
        super("**El Socio ya existe**");
    }
}