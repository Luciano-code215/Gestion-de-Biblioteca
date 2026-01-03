
/**
 * Clase abstracta que representa a un socio de la biblioteca.
 * Un socio posee datos personales y un conjunto de préstamos asociados.
 * 
 * Las subclases definen el tipo específico de socio (por ejemplo, Estudiante o Docente).
 * 
 * @author Luciano Pedotti
 * @version 21/12/2025
 */

import java.util.*;
import java.util.Calendar;

public abstract class Socio
{
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList<Prestamo> prestamos;
    
    /**
     * Constructor que inicializa el socio con una lista de préstamos existente.
     *
     * @param p_dniSocio DNI del socio.
     * @param p_nombre Nombre del socio.
     * @param p_diasPrestamo Cantidad de días permitidos para un préstamo.
     * @param p_prestamos Lista de préstamos asociados al socio.
     */
    public Socio (int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos){
        this.setDni(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }
    
    /**
     * Constructor que inicializa el socio sin préstamos.
     *
     * @param p_dniSocio DNI del socio.
     * @param p_nombre Nombre del socio.
     * @param p_diasPrestamo Cantidad de días permitidos para un préstamo.
     */
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
    
    /**
     * Calcula la cantidad de libros que el socio tiene actualmente prestados.
     * Se consideran solo los préstamos activos (sin fecha de devolución).
     *
     * @return Cantidad de libros prestados.
     */
    public int cantLibrosPrestados(){
        int contador = 0;
        
        for (Prestamo unPrestamo : this.getPrestamos()){
            if (unPrestamo.getFechaDevolucion() == null){
                contador ++;
            }
        }
        
        return contador;
    }
    
    /**
     * Devuelve una descripción textual del socio.
     *
     * @return Representación en texto del socio.
     */
    public String toString(){
        return "DNI: " + this.getDni() + "||" + this.getNombre() + "(" + this.soyDeLaClase() + 
          ") ||" + "Libros Prestados: " + this.cantLibrosPrestados(); 
    }
    
    /**
     * Indica si el socio puede solicitar un nuevo préstamo.
     * Un socio puede pedir libros únicamente si no posee préstamos vencidos activos.
     *
     * @return {@code true} si puede solicitar un préstamo, {@code false} en caso contrario.
     */
    public boolean puedePedir(){
        Calendar hoy = new GregorianCalendar();
        
        for (Prestamo unPrestamo : this.getPrestamos()){
            if (unPrestamo.getFechaDevolucion() == null && unPrestamo.vencido(hoy)){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Devuelve el tipo concreto de socio.
     * Método que debe ser implementado por las subclases.
     *
     * @return Nombre del tipo de socio.
     */
    public abstract String soyDeLaClase();
}