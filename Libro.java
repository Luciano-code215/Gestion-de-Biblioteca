
/**
 * Representa un libro dentro de la biblioteca.
 * Un libro posee información bibliográfica y mantiene
 * el historial de préstamos realizados sobre él.
 * 
 * @author Luciano Pedotti 
 * @version 21/12/2025
 */

import java.util.*;

public class Libro
{
    private String titulo;
    private int edicion;
    private String editorial;
    private int anio;
    private int cantidad;
    private ArrayList<Prestamo> prestamos;
    
    /**
     * Constructor que inicializa un libro con una lista de préstamos existente.
     *
     * @param p_titulo Título del libro.
     * @param p_edicion Edición del libro.
     * @param p_editorial Editorial del libro.
     * @param p_anio Año de publicación.
     * @param p_prestamos Lista de préstamos del libro.
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio, ArrayList<Prestamo> p_prestamos){
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(p_prestamos);
    }
    
     /**
     * Constructor que inicializa un libro sin préstamos.
     *
     * @param p_titulo Título del libro.
     * @param p_edicion Edición del libro.
     * @param p_editorial Editorial del libro.
     * @param p_anio Año de publicación.
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(new ArrayList<>());
    }
    
    private void setTitulo(String p_titulo){
        this.titulo = p_titulo;
    }
    
    private void setEdicion(int p_edicion){
        this.edicion = p_edicion;
    }
    
    private void setEditorial(String p_editorial){
        this.editorial = p_editorial;
    }
    
    private void setAnio(int p_anio){
        this.anio = p_anio;
    }
    
    private void setPrestamos(ArrayList<Prestamo> p_prestamos){
        this.prestamos = p_prestamos;
    }
    
    public String getTitulo(){
        return this.titulo;
    }
    
    public int getEdicion(){
        return this.edicion;
    }
    
    public String getEditorial(){
        return this.editorial;
    }
    
    public int getAnio(){
        return this.anio;
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
     * Indica si el libro se encuentra actualmente prestado.
     *
     * @return {@code true} si existe un préstamo activo,
     *         {@code false} en caso contrario.
     */
    public boolean prestado (){
        for (Prestamo unPrestamo : this.getPrestamos()){
            if (unPrestamo.getFechaDevolucion() == null){
                return true;
            }
        }
        
        return false;
    }
    
     /**
     * Devuelve el último préstamo realizado sobre el libro.
     *
     * @return Último préstamo o {@code null} si no existen préstamos.
     */
    public Prestamo ultimoPrestamo(){
        if(!this.getPrestamos().isEmpty()){
            return this.getPrestamos().get(this.getPrestamos().size() - 1);
        }
        
        return null;
    }
    
     /**
     * Compara dos libros por sus atributos bibliográficos.
     *
     * @param p_libro Libro a comparar.
     * @return {@code true} si ambos libros representan la misma edición,
     *         {@code false} en caso contrario.
     */
    public boolean esIgual(Libro p_libro){
        return (this.getTitulo() == p_libro.getTitulo() && this.getEdicion() == p_libro.getEdicion() && 
          this.getEditorial() == p_libro.getEditorial() && this.getAnio() == p_libro.getAnio());
    }
    
    /**
     * Devuelve una representación textual del libro.
     *
     * @return Título del libro.
     */
    public String toString(){
        return "Titulo: " + this.getTitulo();
    }
}