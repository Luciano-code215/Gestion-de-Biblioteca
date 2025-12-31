
/**
 * Write a description of class LibroPrestadoException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LibroPrestadoException extends Exception
{
    public LibroPrestadoException(){
        super("**El libro se encuentra prestado**");
    }
}