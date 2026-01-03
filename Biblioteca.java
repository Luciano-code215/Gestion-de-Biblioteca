/**
 * La clase Biblioteca modela una biblioteca que administra socios y libros.
 * Permite registrar socios de distintos tipos, incorporar libros al catálogo,
 * realizar préstamos y devoluciones, y obtener información sobre el estado
 * de los libros y socios.
 *
 * La biblioteca controla las reglas de préstamo según el tipo de socio,
 * valida la disponibilidad de los libros y gestiona los préstamos vencidos
 * y docentes responsables.
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
    
     /**
     * Constructor que inicializa la biblioteca con socios y libros existentes.
     *
     * @param p_nombre Nombre de la biblioteca.
     * @param p_socios Lista de socios.
     * @param p_libros Lista de libros.
     */
    public Biblioteca (String p_nombre, ArrayList<Socio> p_socios, ArrayList<Libro> p_libros){
        this.setNombre(p_nombre);
        this.setSocios(p_socios);
        this.setLibros(p_libros);
    }
    
    /**
     * Constructor que inicializa una biblioteca vacía.
     *
     * @param p_nombre Nombre de la biblioteca.
     */
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
    
    /**
     * Elimina un socio si no posee préstamos activos.
     *
     * @param p_socio Socio a eliminar.
     * @return {@code true} si el socio fue eliminado.
     */
    public boolean quitarSocio(Socio p_socio){
        for (Prestamo unPrestamo : p_socio.getPrestamos()){
            if (unPrestamo.getFechaDevolucion() == null){
                System.out.println("**Primero debe hacer la devolucion de los prestamos**");
                return false;
            }
        }
        return this.getSocios().remove(p_socio);
    }
    
     /**
     * Elimina un libro si no se encuentra prestado.
     *
     * @param p_libro Libro a eliminar.
     * @return {@code true} si el libro fue eliminado.
     */
    public boolean quitarLibro(Libro p_libro){
        if(p_libro.prestado()){
            System.out.println("**Primero debe devolverse el libro**");
            return false;
        }
        return this.getLibros().remove(p_libro);
    }
    
    /**
     * Crea un nuevo libro y lo agrega a la biblioteca.
     *
     * @param p_titulo Título del libro.
     * @param p_edicion Número de edición.
     * @param p_editorial Editorial del libro.
     * @param p_anio Año de publicación.
     */
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        Libro libro = new Libro(p_titulo, p_edicion, p_editorial, p_anio);
        
        if(this.agregarLibro(libro)){
            System.out.println("Libro " + libro.getTitulo() + " agregado con exito");
        }
    }
    
    /**
     * Crea y registra un nuevo socio de tipo Estudiante.
     *
     * @param p_dniSocio DNI del socio.
     * @param p_nombre Nombre del socio.
     * @param p_carrera Carrera que cursa el estudiante.
     * @throws SocioDuplicadoException Si ya existe un socio con el DNI indicado.
     */
    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera)throws SocioDuplicadoException{
        if (this.socioExistente(p_dniSocio)){
            throw new SocioDuplicadoException ();
        }
        
        Estudiante estudiante = new Estudiante(p_dniSocio, p_nombre, 20, p_carrera);
        if(this.agregarSocio(estudiante)){
            System.out.println("Estudiante " + p_nombre + " agregado con exito");
        }
    }
    
    /**
     * Crea y registra un nuevo socio de tipo Docente.
     *
     * @param p_dniSocio DNI del socio.
     * @param p_nombre Nombre del socio.
     * @param p_area Área de desempeño del docente.
     * @throws SocioDuplicadoException Si ya existe un socio con el DNI indicado.
     */
    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area)throws SocioDuplicadoException{
        if (this.socioExistente(p_dniSocio)){
            throw new SocioDuplicadoException ();
        }
        
        Docente docente = new Docente(p_dniSocio, p_nombre, 5, p_area);
        if(this.agregarSocio(docente)){
            System.out.println("Docente " + p_nombre + " agregado con exito");
        }
    }
    
    /**
     * Registra el préstamo de un libro a un socio.
     *
     * @param p_fechaRetiro Fecha en la que se realiza el préstamo.
     * @param p_socio Socio que solicita el libro.
     * @param p_libro Libro a prestar.
     * @return {@code true} si el préstamo se realizó correctamente,
     *         {@code false} en caso contrario.
     * @throws LibroPrestadoException Si el libro ya se encuentra prestado.
     */
    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro)throws LibroPrestadoException{
        if (p_fechaRetiro == null || p_socio == null || p_libro == null){
            return false;
        }
        
        if (p_libro.prestado()){
            throw new LibroPrestadoException();
        }
        
         if(p_socio.puedePedir()){
            Prestamo prestamo = new Prestamo (p_fechaRetiro, p_socio, p_libro);
            p_socio.agregarPrestamo(prestamo);
            p_libro.agregarPrestamo(prestamo);
            return true;
        }
        
        return false;
    }
    
    /**
     * Registra la devolución de un libro prestado.
     *
     * @param p_libro Libro a devolver.
     * @throws LibroNoPrestadoException Si el libro no se encuentra prestado.
     */
    public void devolverLibro(Libro p_libro)throws LibroNoPrestadoException{
        if (p_libro.prestado()){
            throw new LibroNoPrestadoException();
        }
        
        Calendar fechaHoy = new GregorianCalendar();
        p_libro.ultimoPrestamo().registrarFechaDevolucion(fechaHoy);
    }
    
    /**
     * Calcula la cantidad de socios según su tipo.
     *
     * @param p_objeto Tipo de socio a contar (por ejemplo "Estudiante" o "Docente").
     * @return Cantidad de socios del tipo indicado.
     */
    public int cantidadSociosPorTipo(String p_objeto){
        int cant = 0;
        
        for (Socio unSocio : this.getSocios()){
            if (unSocio.soyDeLaClase().equalsIgnoreCase(p_objeto)){
                cant ++;
            }
        }
        
        return cant;
    }
    
    /**
     * Obtiene la lista de préstamos que se encuentran vencidos
     * a la fecha actual.
     *
     * @return Lista de préstamos vencidos.
     */
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
    
    /**
     * Obtiene la lista de docentes responsables.
     * Un docente es responsable si nunca tuvo préstamos vencidos.
     *
     * @return Lista de docentes responsables.
     */
    public ArrayList<Socio> docentesResponsables(){
        ArrayList<Socio> responsables = new ArrayList<>();
        
        for (Socio unSocio : this.getSocios()){
            if (unSocio instanceof Docente && ((Docente)unSocio).esResponsable()){
                responsables.add(unSocio);
            }
        }
        
        return responsables;
    }
    
    /**
     * Devuelve el nombre del socio que tiene prestado un libro.
     *
     * @param p_libro Libro a consultar.
     * @return Nombre del socio que posee el libro.
     * @throws LibroNoPrestadoException Si el libro no se encuentra prestado.
     */
    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException{
        if(!p_libro.prestado()){
            throw new LibroNoPrestadoException();
        }
        
        return p_libro.ultimoPrestamo().getSocio().getNombre();
    }
    
    /**
     * Devuelve una lista detallada de todos los socios registrados,
     * incluyendo estadísticas por tipo.
     *
     * @return Cadena con la información de los socios.
     */
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
    
    /**
     * Busca un socio por su número de DNI.
     *
     * @param p_dni DNI del socio a buscar.
     * @return El socio encontrado o {@code null} si no existe.
     */
    public Socio buscarSocio(int p_dni){
        for (Socio unSocio : this.getSocios()){
            if (unSocio.getDni() == p_dni){
                return unSocio;
            }
        }
        
        return null;
    }
    
    /**
     * Devuelve una lista con los títulos de todos los libros.
     *
     * @return Cadena con los títulos de los libros.
     */
    public String listaDeTitulos(){
        String cadena = "";
        
        for  (Libro unLibro : this.getLibros()){
            cadena = cadena + unLibro.toString() + "\n";
        }
        
        return cadena;
    }
    
    /**
     * Devuelve una lista de libros con su estado de préstamo.
     *
     * @return Cadena con la información de los libros.
     */
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
    
    /**
     * Devuelve una lista de docentes responsables.
     *
     * @return Cadena con la información de los docentes responsables.
     */
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
    
    /**
     * Verifica si existe un socio registrado con un DNI determinado.
     *
     * @param p_dni DNI a verificar.
     * @return {@code true} si el socio existe, {@code false} en caso contrario.
     */
    public boolean socioExistente (int p_dni){
        for(Socio unSocio : this.getSocios()){
            if (unSocio.getDni() == p_dni){
                return true;
            }
        }
        
        return false;
    }
}