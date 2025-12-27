
/**
 * Write a description of class Biblioteca here.
 * 
 * @author Luciano Pedotti
 * @version 24/12/2025
 */

import java.util.*;
import java.util.GregorianCalendar;

public class Biblioteca
{
    String nombre;
    ArrayList<Socio> socios;
    ArrayList<Libro> libros;
    
    public Biblioteca (String p_nombre, ArrayList<Socio> p_socios, ArrayList<Libro> p_libros){
        this.setNombre(p_nombre);
        this.setSocios(p_socios);
        this.setLibros(p_libros);
    }
    
    public Biblioteca (String p_nombre){
        this.setNombre(p_nombre);
        this.setSocios (new ArrayList<Socio>());
        this.setLibros (new ArrayList<Libro>());
    }
    
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }
    
    private void setSocios(ArrayList<Socio> p_socios){
        this.socios = p_socios;
    }
    
    private void setLibros(ArrayList<Libro> p_libros){
        this.libros = p_libros;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public ArrayList<Socio> getSocios(){
        return this.socios;
    }
    
    public ArrayList<Libro> getLibros(){
        return this.libros;
    }
    
    public boolean agregarSocio(Socio p_socio){
        return this.getSocios().add(p_socio);
    }
    
    public boolean agregarLibro(Libro p_libro){
        return this.getLibros().add(p_libro);
    }
    
    public boolean quitarSocio(Socio p_socio){
        return this.getSocios().remove(p_socio);
    }
    
    public boolean quitarLibro(Libro p_libro){
        return this.getLibros().remove(p_libro);
    }
    
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        Libro libro = new Libro(p_titulo, p_edicion, p_editorial, p_anio);
        this.agregarLibro(libro);
    }
    
    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera){
        Estudiante estudiante = new Estudiante(p_dniSocio, p_nombre, 20, p_carrera);
        this.agregarSocio(estudiante);
    }
    
    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area){
        Docente docente = new Docente(p_dniSocio, p_nombre, 5, p_area);
        this.agregarSocio(docente);
    }
    
    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro){
        if (p_fechaRetiro == null || p_socio == null || p_libro == null){
            return false;
        }
        
        if (p_libro.prestado()){
            return false;
        }
        
        Prestamo prestamo = new Prestamo (p_fechaRetiro, p_socio, p_libro);
        
        p_socio.agregarPrestamo(prestamo);
        p_libro.agregarPrestamo(prestamo);
        
        return true;
    }
    
    public void devolverLibro(Libro p_libro){
        Calendar fechaHoy = new GregorianCalendar();
        
        if (p_libro.prestado()){
            p_libro.ultimoPrestamo().registrarFechaDevolucion(fechaHoy);
        }
        else{
            System.out.println("El libro no se encuentra prestado");
        }
    }
    
    public int cantidadSociosPorTipo(String p_objeto){
        int cant = 0;
        
        for (Socio unSocio : this.getSocios()){
            if (unSocio.soyDeLaClase().equalsIgnoreCase(p_objeto)){
                cant ++;
            }
        }
        
        return cant;
    }
    
    public ArrayList<Prestamo> prestamosVencidos(){
        ArrayList<Prestamo> vencidos = new ArrayList<>();
        Calendar fechaHoy = new GregorianCalendar();
        
        for (Libro unLibro : this.getLibros()){
            if (unLibro.prestado() && unLibro.ultimoPrestamo().vencido(fechaHoy)){
                vencidos.add(unLibro.ultimoPrestamo());
            }
        }
        
        return vencidos;
    }
    
    public ArrayList<Socio> docentesResponsables(){
        ArrayList<Socio> responsables = new ArrayList<>();
        
        for (Socio unSocio : this.getSocios()){
            if (unSocio instanceof Docente && ((Docente)unSocio).esResponsable()){
                responsables.add(unSocio);
            }
        }
        
        return responsables;
    }
    
    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException{
        if(!p_libro.prestado()){
            throw new LibroNoPrestadoException("El libro se encuenta en biblioteca");
        }
        
        return p_libro.ultimoPrestamo().getSocio().getNombre();
    }
    
    public String listaDeSocios(){
        String cadena = "";
        
        for (Socio unSocio : this.getSocios()){
            cadena = cadena + unSocio.toString() + "\n";
        }
        
        return cadena + "*******************\n" + "Cantidad de socios por tipo Estudiante: " +
          this.cantidadSociosPorTipo("Estudiante") + "\nCantidad de socios por tipo Docente: " +
          this.cantidadSociosPorTipo("Docente") + "\n*******************";
    }
    
    public Socio buscarSocio(int p_dni){
        for (Socio unSocio : this.getSocios()){
            if (unSocio.getDni() == p_dni){
                return unSocio;
            }
        }
        
        return null;
    }
    
    public String listaDeTitulos(){
        String cadena = "";
        
        for  (Libro unLibro : this.getLibros()){
            cadena = cadena + unLibro.toString() + "\n";
        }
        
        return cadena;
    }
    
    public String listaDeLibros(){
        String cadena = "";
        
        for (Libro unLibro : this.getLibros()){
            cadena = cadena + unLibro.toString() + "|| Prestado: " + 
              (unLibro.prestado() ? "SI" : "NO") + "\n"; 
        }
        
        return cadena;
    }
    
    public String listaDeDocentesResponsables(){
        String cadena = "";
        
        for (Socio unSocio : this.getSocios()){
            if (unSocio instanceof Docente && ((Docente)unSocio).esResponsable()){
                cadena = cadena + unSocio.toString() + "\n";
            }
        }
        
        return cadena;
    }
}