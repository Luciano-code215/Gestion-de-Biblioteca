
/**
 * Write a description of class LibroNoPrestadoException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LibroNoPrestadoException extends Exception
{
    public LibroNoPrestadoException(){
        super("**El libro esta en biblioteca**");
    }
}