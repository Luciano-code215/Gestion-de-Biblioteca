
/**
 * Write a description of class Estudiante here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Estudiante extends Socio
{
    private String carrera;
    
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamos);
        this.setCarrera(p_carrera);
    }
    
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setCarrera(p_carrera);
    }
    
    private void setCarrera(String p_carrera){
        this.carrera = p_carrera;
    }
    
    public String getCarrera(){
        return this.carrera;
    }
    
    public boolean puedePedir(){
        return (super.puedePedir() && this.cantLibrosPrestados() <= 3);
    }
    
    public String soyDeLaClase(){
        return "estudiante";
    }
}