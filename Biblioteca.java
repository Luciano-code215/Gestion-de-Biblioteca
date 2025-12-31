
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
        try{
            return this.getSocios().add(p_socio);
        }catch (OutOfMemoryError e){
            System.err.println("Error fatal: No hay suficiente espacio para agregar Socios");
            return false;
        }
    }
    
    public boolean agregarLibro(Libro p_libro){
        try{
            return this.getLibros().add(p_libro);
        }catch (OutOfMemoryError e){
            System.err.println("Error fatal: No hay suficiente espacio para agregar Libros");
            return false;
        }        
    }
    
    public boolean quitarSocio(Socio p_socio){
        return this.getSocios().remove(p_socio);
    }
    
    public boolean quitarLibro(Libro p_libro){
        return this.getLibros().remove(p_libro);
    }
    
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        Libro libro = new Libro(p_titulo, p_edicion, p_editorial, p_anio);
        
        if(this.agregarLibro(libro)){
            System.out.println("Libro " + libro.getTitulo() + " agregado con exito");
        }
    }
    
    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera)throws SocioDuplicadoException{
        if (this.socioExistente(p_dniSocio)){
            throw new SocioDuplicadoException ();
        }
        
        Estudiante estudiante = new Estudiante(p_dniSocio, p_nombre, 20, p_carrera);
        if(this.agregarSocio(estudiante)){
            System.out.println("Estudiante " + p_nombre + " agregado con exito");
        }
    }
    
    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area)throws SocioDuplicadoException{
        if (this.socioExistente(p_dniSocio)){
            throw new SocioDuplicadoException ();
        }
        
        Docente docente = new Docente(p_dniSocio, p_nombre, 5, p_area);
        if(this.agregarSocio(docente)){
            System.out.println("Docente " + p_nombre + " agregado con exito");
        }
    }
    
    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro)throws LibroPrestadoException{
        if (p_fechaRetiro == null || p_socio == null || p_libro == null){
            return false;
        }
        
        if (p_libro.prestado()){
            throw new LibroPrestadoException();
        }
        
        Prestamo prestamo = new Prestamo (p_fechaRetiro, p_socio, p_libro);
        
        p_socio.agregarPrestamo(prestamo);
        p_libro.agregarPrestamo(prestamo);
        
        return true;
    }
    
    public void devolverLibro(Libro p_libro)throws LibroNoPrestadoException{
        if (p_libro.prestado()){
            throw new LibroNoPrestadoException();
        }
        
        Calendar fechaHoy = new GregorianCalendar();
        p_libro.ultimoPrestamo().registrarFechaDevolucion(fechaHoy);
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
            throw new LibroNoPrestadoException();
        }
        
        return p_libro.ultimoPrestamo().getSocio().getNombre();
    }
    
    public String listaDeSocios(){
        String cadena = "";
        int indice = 1;
        
        for (Socio unSocio : this.getSocios()){
            cadena = cadena + indice + " - " + unSocio.toString() + "\n";
            indice++;
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
        int indice = 1;
        
        for (Libro unLibro : this.getLibros()){
            cadena = cadena + indice + " - " + unLibro.toString() + "|| Prestado: " + 
              (unLibro.prestado() ? "SI" : "NO") + "\n"; 
            indice ++;
        }
        
        return cadena;
    }
    
    public String listaDeDocentesResponsables(){
        if(this.docentesResponsables().isEmpty()){
            return "\t****NO HAY DOCENTES RESPONSABLES****";
                        }
        
        String cadena = "\t****DOCENTES RESPONSABLES****\n";
        
        for (Socio unSocio : this.docentesResponsables()){
            cadena = cadena + unSocio.toString() + "\n";
        }
        
        return cadena;
    }
    
    public boolean socioExistente (int p_dni){
        for(Socio unSocio : this.getSocios()){
            if (unSocio.getDni() == p_dni){
                return true;
            }
        }
        
        return false;
    }
}