
/**
 * Write a description of class Coneccion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Conexion{
    private static final String URL = "jdbc:sqlite:biblioteca1.db";
    
    public static Connection conectar(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e){
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conn;
    }
    
    public static void crearTablas(){
        String sqlSocios = "CREATE TABLE IF NOT EXISTS socios (dni INTEGER PRIMARY KEY, " +
            "nombre TEXT NO NULL, dias_prestamo INTEGER, tipo TEXT NO NULL, adicional TEXT);";
          
        String sqlLibros = "CREATE TABLE IF NOT EXISTS libros (id INTEGER PRIMARY KEY AUTOINCREMENT, "+
          "titulo TEXT NO NULL, edicion INTEGER, editorial TEXT, anio INTEGER);";
          
        String sqlPrestamos = "CREATE TABLE IF NOT EXISTS prestamos (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
          "fechaRetiro TEXT NO NULL, fechaDevolucion TEXT, dni_socio INTEGER, id_libro INTEGER, FOREIGN KEY(dni_socio) REFERENCES socios(dni), "+
            "FOREIGN KEY(id_libro) REFERENCES libros(id));";
          
        try (Connection conn = conectar();
            Statement stmt = conn.createStatement()){
            
            stmt.execute(sqlSocios);
            stmt.execute(sqlLibros);
            stmt.execute(sqlPrestamos);
        }catch (SQLException e){
            System.out.println("Error al crear tablas" + e.getMessage());
        }
    }
    
    public static void guardarEstudiante (Estudiante p_estudiante) {
        String sql = "INSERT INTO socios(dni, nombre, dias_prestamo, tipo, adicional) VALUES (?,?,?,?,?)";
        
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                
            pstmt.setInt(1, p_estudiante.getDni()); 
            pstmt.setString(2, p_estudiante.getNombre());
            pstmt.setInt(3, p_estudiante.getDiasPrestamo());
            pstmt.setString(4, "estudiante");
            pstmt.setString(5, p_estudiante.getCarrera());
            
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error al guardar estudiante: " + e.getMessage());
        }
    }
    
    public static void guardarDocente (Docente p_docente){
        String sql = "INSERT INTO socios(dni, nombre, dias_prestamo, tipo, adicional) VALUES (?,?,?,?,?)";
        
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                
            pstmt.setInt(1, p_docente.getDni());
            pstmt.setString(2, p_docente.getNombre());
            pstmt.setInt(3, p_docente.getDiasPrestamo());
            pstmt.setString(4, "docente");
            pstmt.setString(5, p_docente.getArea());
            
            pstmt.executeUpdate();
        }catch (SQLException e){
                System.out.println("Error al guardar docente: " + e.getMessage());
        }
    }
    
    public static int guardarLibro (Libro p_libro){
        String sql = "INSERT INTO libros(titulo, edicion, editorial, anio) VALUES (?,?,?,?)";
        int idGenerado = -1;
        
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                
            pstmt.setString(1, p_libro.getTitulo());
            pstmt.setInt(2, p_libro.getEdicion());
            pstmt.setString(3, p_libro.getEditorial());
            pstmt.setInt(4, p_libro.getAnio());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
            
        }catch (SQLException e){
            System.out.println("Error al guardar libro: " + e.getMessage());
        }
        
        return idGenerado;
    }
    
    public static int guardarPrestamo (Prestamo p_prestamo){
        String sql = "INSERT INTO prestamos(fechaRetiro, dni_socio, id_libro) VALUES (?,?,?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int idGenerado = -1;
        
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            String fechaTexto = sdf.format(p_prestamo.getFechaRetiro().getTime());
            pstmt.setString(1, fechaTexto);
            pstmt.setInt(2, p_prestamo.getSocio().getDni());
            pstmt.setInt(3, p_prestamo.getLibro().getId());
            
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    idGenerado = rs.getInt(1);
                }
            }
        }catch (SQLException e){
            System.out.println("Error al guardar prestamo: " + e.getMessage());
        }
        return idGenerado;
    }
    
    public static ArrayList<Libro> recuperarLibros(){
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        
        try (Connection conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
                
            while (rs.next()){
                Libro libro = new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getInt("edicion"),
                    rs.getString("editorial"),
                    rs.getInt("anio")
                );
                lista.add(libro);
            }
        }catch (SQLException e){
            System.out.println("Error al recuperar libros: " + e.getMessage());
        }
        return lista;
    }
    
    public static ArrayList<Socio> recuperarSocios(){
        ArrayList<Socio> lista = new ArrayList<>();
        String sql = "SELECT * FROM socios";
        
        try (Connection conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()){
                int dni = rs.getInt("dni");
                String nombre = rs.getString("nombre");
                int diasPrestamo = rs.getInt("dias_prestamo");
                String tipo = rs.getString("tipo");
                String adicional = rs.getString("adicional");
                
                if(tipo.equalsIgnoreCase("docente")){
                    lista.add(new Docente (dni, nombre, diasPrestamo, adicional));
                }else{
                    lista.add(new Estudiante (dni, nombre, diasPrestamo, adicional));
                }
            }
        }catch (SQLException e){
            System.out.println("Error al recuperar socios: " + e.getMessage());
        }
        return lista;
    }
    
    public static ArrayList<Prestamo> recuperarPrestamos(ArrayList<Socio> p_socios, ArrayList<Libro> p_libros) throws java.text.ParseException {
        ArrayList<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try(Connection conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
                
            while (rs.next()){
                String fechaRString = rs.getString("fechaRetiro");
                String fechaDString = rs.getString("fechaDevolucion");
                int dniSocio = rs.getInt("dni_socio");
                int idLibro = rs.getInt("id_libro");
                
                Socio socio = buscarSocioEnLista(p_socios, dniSocio);
                Libro libro = buscarLibroEnLista(p_libros, idLibro);
                
                Calendar retiro = Calendar.getInstance();
                Calendar devolucion = Calendar.getInstance();
                
                if(fechaRString != null){
                    retiro.setTime(sdf.parse(fechaRString));
                }
                
                if(fechaDString != null){
                    devolucion.setTime(sdf.parse(fechaDString));
                }
                else {
                    devolucion = null;
                }
                
                if(socio != null && libro != null){
                    Prestamo prestamo = new Prestamo (retiro, socio, libro);
                    prestamo.registrarFechaDevolucion(devolucion);
                    lista.add(prestamo);
                    socio.agregarPrestamo(prestamo);
                    libro.agregarPrestamo(prestamo);
                }
            }
        }catch (SQLException e){
            System.out.println("Error al recuperar prestamos: " + e.getMessage());
        }
    
        return lista;
    }
    
    public static Socio buscarSocioEnLista(ArrayList<Socio> p_socios, int p_dni){
        for (Socio unSocio : p_socios){
            if (p_dni == unSocio.getDni()){
                return unSocio;
            }
        }
        return null;
    }
    
    public static Libro buscarLibroEnLista(ArrayList<Libro> p_libros, int p_id){
        for (Libro unLibro : p_libros){
            if (p_id == unLibro.getId()){
                return unLibro;
            }
        }
        return null;
    }
    
    public static boolean eliminarSocioBD(int dni) {
        String sql = "DELETE FROM socios WHERE dni = ?";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setInt(1, dni);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        
        } catch (SQLException e) {
            System.out.println("Error al eliminar socio (posiblemente tenga préstamos): " + e.getMessage());
            return false;
        }
    }
    
    public static boolean eliminarLibroBD(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        
        } catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean registrarDevolucionBD(int dniSocio, int idLibro) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        String fechaHoy = sdf.format(new java.util.Date());
        // Solo actualizamos si fecha_devo es NULL (o sea, está activo)
        String sql = "UPDATE prestamos SET fechaDevolucion = ? " +
                 "WHERE dni_socio = ? AND id_libro = ? AND fechaDevolucion IS NULL";

        try  (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fechaHoy);
            pstmt.setInt(2, dniSocio);
            pstmt.setInt(3, idLibro);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // Si es 0, es que no había un préstamo activo para ese socio/libro

        } catch (SQLException e) {
            System.out.println("Error al actualizar devolución en BD: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarDiasPrestamoBD(int p_dni, int p_nuevosDias){
        String sql = "UPDATE socios SET dias_prestamo = ? WHERE dni = ?";
        
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
        
            pstmt.setInt(1, p_nuevosDias);
            pstmt.setInt(2, p_dni);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        }catch (SQLException e){
            System.out.println("Error al actualizar dias de prestamos en BD: " + e.getMessage());
            return false;
        }
    }
}