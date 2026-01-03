
/**
 * Representa un préstamo de un libro a un socio.
 * Un préstamo registra la fecha de retiro, la posible fecha de devolución,
 * el socio que realiza el préstamo y el libro prestado.
 * 
 * @author Luciano Pedotti
 * @version 21/12/2025
 */

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Prestamo
{
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Socio socio;
    private Libro libro;
    
     /**
     * Constructor que crea un préstamo activo.
     * La fecha de devolución se inicializa como {@code null}.
     *
     * @param p_retiro Fecha en la que se retira el libro.
     * @param p_socio Socio que realiza el préstamo.
     * @param p_libro Libro que se presta.
     */
    public Prestamo (Calendar p_retiro, Socio p_socio, Libro p_libro){
        this.setFechaRetiro(p_retiro);
        this.setFechaDevolucion(null);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }
    
    private void setFechaRetiro(Calendar p_fecha){
        this.fechaRetiro = p_fecha;
    }
    
    private void setFechaDevolucion(Calendar p_fecha){
        this.fechaDevolucion = p_fecha;
    }
    
    private void setSocio(Socio p_socio){
        this.socio = p_socio;
    }
    
    private void setLibro(Libro p_libro){
        this.libro = p_libro;
    }
    
    public Calendar getFechaRetiro(){
        return this.fechaRetiro;
    }
    
    public Calendar getFechaDevolucion(){
        return this.fechaDevolucion;
    }
    
    public Socio getSocio(){
        return this.socio;
    }
    
    public Libro getLibro(){
        return this.libro;
    }
    
    /**
     * Registra la fecha de devolución del libro.
     *
     * @param p_fecha Fecha en la que se devuelve el libro.
     */
    public void registrarFechaDevolucion(Calendar p_fecha){
        this.setFechaDevolucion(p_fecha);
    }
    
     /**
     * Indica si el préstamo se encuentra vencido en relación a una fecha dada.
     * La fecha límite se calcula sumando los días permitidos por el socio
     * a la fecha de retiro.
     *
     * @param p_fecha Fecha contra la cual se evalúa el vencimiento.
     * @return {@code true} si el préstamo está vencido, {@code false} en caso contrario.
     */
    public boolean vencido(Calendar p_fecha){
        int dias = this.getSocio().getDiasPrestamo();
        Calendar fechaLimite = (Calendar) this.getFechaRetiro().clone();
        fechaLimite.add(Calendar.DATE , dias);
        
        return p_fecha.after(fechaLimite);
    }
    
     /**
     * Devuelve una representación en texto del préstamo,
     * incluyendo fechas, libro y socio.
     *
     * @return Cadena descriptiva del préstamo.
     */
    public String toString(){
        Calendar retiro = (Calendar) this.getFechaRetiro().clone();
        String retiroFormateado = String.format("%tF", retiro);
        String devoFormateado = "No fue devuelto aun";
        
        if(this.getFechaDevolucion() != null){
            Calendar devolucion = (Calendar) this.getFechaDevolucion().clone();
            devoFormateado = String.format("%tF", devolucion);
        }
        return "Retiro: " + retiroFormateado + " - Devolucion: " + devoFormateado + "\n" +
          "Libro: " + this.getLibro().getTitulo() + "\n" +
          "Socio: " + this.getSocio().getNombre();
    }
}