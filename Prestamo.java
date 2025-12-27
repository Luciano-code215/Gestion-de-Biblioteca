
/**
 * Write a description of class Prestamo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
    
    public void registrarFechaDevolucion(Calendar p_fecha){
        this.setFechaDevolucion(p_fecha);
    }
    
    public boolean vencido(Calendar p_fecha){
        int dias = this.getSocio().getDiasPrestamo();
        Calendar fechaLimite = (Calendar) this.getFechaRetiro().clone();
        fechaLimite.add(Calendar.DATE , dias);
        
        return p_fecha.before(fechaLimite) || p_fecha.equals(fechaLimite);
    }
    
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