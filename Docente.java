/**
 * Write a description of class Docente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Docente extends Socio
{
    private String area;
    
    public Docente (int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos,
      String p_area){
          super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamos);
          this.setArea(p_area);
      }
      
    public Docente (int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_area){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setArea(p_area);
    }
    
    private void setArea(String p_area){
        this.area = p_area;
    }
    
    public String getArea(){
        return this.area;
    }
    
    /**
     * si la fecha es nula compara con la fecha de hoy
     */
    public boolean esResponsable(){
        for (Prestamo unPrestamo : this.getPrestamos()){
            Calendar fechaAComparar = (unPrestamo.getFechaDevolucion() != null) ? 
                                        unPrestamo.getFechaDevolucion() :
                                        new GregorianCalendar();
            if(unPrestamo.vencido(fechaAComparar)){
                return false;
            }
        }
        
        return true;
    }
    
    public void cambiarDiasDePrestamo(int p_dias){
        if(this.esResponsable()){
            this.setDiasPrestamo(this.getDiasPrestamo() + p_dias);
        }
    }
    
    public boolean puedePedir(){
        return super.puedePedir();
    }
    
    public String soyDeLaClase(){
        return "docente";
    }
}
