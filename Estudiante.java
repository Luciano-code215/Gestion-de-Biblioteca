
/**
 * Representa a un socio del tipo Estudiante.
 * Un estudiante pertenece a una carrera y tiene un límite
 * máximo de libros que puede solicitar en simultáneo.
 * 
 * @author Luciano Pedotti
 * @version 21/12/2025
 */

import java.util.*;

public class Estudiante extends Socio
{
    private String carrera;
    
    /**
     * Constructor que inicializa un estudiante con una lista de préstamos existente.
     *
     * @param p_dniSocio DNI del estudiante.
     * @param p_nombre Nombre del estudiante.
     * @param p_diasPrestamo Cantidad de días permitidos para los préstamos.
     * @param p_prestamos Lista de préstamos asociados al estudiante.
     * @param p_carrera Carrera que cursa el estudiante.
     */
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamos);
        this.setCarrera(p_carrera);
    }
    
    /**
     * Constructor que inicializa un estudiante sin préstamos.
     *
     * @param p_dniSocio DNI del estudiante.
     * @param p_nombre Nombre del estudiante.
     * @param p_diasPrestamo Cantidad de días permitidos para los préstamos.
     * @param p_carrera Carrera que cursa el estudiante.
     */
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
    
     /**
     * Indica si el estudiante puede solicitar un nuevo préstamo.
     * Un estudiante puede pedir libros solo si:
     * 
     *    No posee préstamos vencidos.
     *    No supera el límite de 3 libros prestados simultáneamente.
     *
     * @return {@code true} si puede solicitar un préstamo, {@code false} en caso contrario.
     */
    @Override
    public boolean puedePedir(){
        return (super.puedePedir() && this.cantLibrosPrestados() < 3);
    }
    
    /**
     * Devuelve el tipo concreto de socio.
     *
     * @return Cadena que identifica al estudiante.
     */
    public String soyDeLaClase(){
        return "estudiante";
    }
}