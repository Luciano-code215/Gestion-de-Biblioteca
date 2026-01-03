/**
 * Representa a un socio del tipo Docente.
 * Un docente pertenece a un área específica y puede modificar
 * la cantidad de días de préstamo si demuestra ser responsable.
 * 
 * @author Luciano Pedotti 
 * @version 21/12/2025
 */

import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Docente extends Socio
{
    private String area;
    
    /**
     * Constructor que inicializa un docente con una lista de préstamos existente.
     *
     * @param p_dniSocio DNI del docente.
     * @param p_nombre Nombre del docente.
     * @param p_diasPrestamo Cantidad de días permitidos para los préstamos.
     * @param p_prestamos Lista de préstamos asociados al docente.
     * @param p_area Área académica del docente.
     */
    public Docente (int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos,
      String p_area){
          super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamos);
          this.setArea(p_area);
      }
    
     /**
     * Constructor que inicializa un docente sin préstamos.
     *
     * @param p_dniSocio DNI del docente.
     * @param p_nombre Nombre del docente.
     * @param p_diasPrestamo Cantidad de días permitidos para los préstamos.
     * @param p_area Área académica del docente.
     */
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
     * Indica si el docente es responsable.
     * Un docente es responsable si no posee préstamos vencidos,
     * considerando la fecha de devolución o la fecha actual si
     * el préstamo aún está activo.
     *
     * @return {@code true} si es responsable, {@code false} en caso contrario.
     */
    public boolean esResponsable(){
        if (this.getPrestamos() == null || this.getPrestamos().isEmpty()) {
        return true; 
        }
        
        Calendar hoy = new GregorianCalendar();
        Calendar fechaAComparar;
        
        
        for (Prestamo unPrestamo : this.getPrestamos()){
            fechaAComparar = (unPrestamo.getFechaDevolucion() != null) ? 
                                        unPrestamo.getFechaDevolucion() :
                                        hoy;
            if(unPrestamo.vencido(fechaAComparar)){
                return false;
            }
        }
        
        return true;
    }
    
     /**
     * Incrementa la cantidad de días permitidos para los préstamos
     * únicamente si el docente es considerado responsable.
     *
     * @param p_dias Cantidad de días a incrementar.
     */
    public void cambiarDiasDePrestamo(int p_dias){
        if(this.esResponsable()){
            this.setDiasPrestamo(this.getDiasPrestamo() + p_dias);
        }
    }
    
    /**
     * Indica si el docente puede solicitar un nuevo préstamo.
     * Delegado a la implementación de la clase {@link Socio}.
     *
     * @return {@code true} si puede solicitar un préstamo.
     */
    public boolean puedePedir(){
        return super.puedePedir();
    }
    
    /**
     * Devuelve el tipo concreto de socio.
     *
     * @return Cadena que identifica al docente.
     */
    public String soyDeLaClase(){
        return "docente";
    }
}
