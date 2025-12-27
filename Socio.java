
/**
 * Write a description of class Socio here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.util.Calendar;

public abstract class Socio
{
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList<Prestamo> prestamos;
    
    public Socio (int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos){
        this.setDni(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }
    
    public Socio (int p_dniSocio, String p_nombre, int p_diasPrestamo){
        this.setDni(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<>());
    }
    
    private void setDni(int p_dni){
        this.dniSocio = p_dni;
    }
    
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }
    
    protected void setDiasPrestamo(int p_diasPrestamo){
        this.diasPrestamo = p_diasPrestamo;
    }
    
    private void setPrestamos(ArrayList<Prestamo> p_prestamos){
        this.prestamos = p_prestamos;
    }
    
    public int getDni(){
        return this.dniSocio;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public int getDiasPrestamo(){
        return this.diasPrestamo;
    }
    
    public ArrayList<Prestamo> getPrestamos(){
        return this.prestamos;
    }
    
    public boolean agregarPrestamo (Prestamo p_prestamo){
        return this.getPrestamos().add(p_prestamo);
    }
    
    public boolean quitarPrestamo (Prestamo p_prestamo){
        return this.getPrestamos().remove(p_prestamo);
    }
    
    public int cantLibrosPrestados(){
        int contador = 0;
        
        for (Prestamo unPrestamo : this.getPrestamos()){
            if (unPrestamo.getFechaDevolucion() == null){
                contador ++;
            }
        }
        
        return contador;
    }
    
    public String toString(){
        return "DNI: " + this.getDni() + "||" + this.getNombre() + "(" + this.soyDeLaClase() + 
          ") ||" + "Libros Prestados: " + this.cantLibrosPrestados(); 
    }
    
    /**
     * puede pedir solo si la fecha devo es nula, osea esta activo el prestamo, y no esta vencido
     */
    public boolean puedePedir(){
        for (Prestamo unPrestamo : this.getPrestamos()){
            if (unPrestamo.getFechaDevolucion() == null && unPrestamo.vencido(new GregorianCalendar())){
                return false;
            }
        }
        
        return true;
    }
    
    public abstract String soyDeLaClase();
}