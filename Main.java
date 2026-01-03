
/**
  * La clase Main contiene el método principal del programa.
 * Se encarga de interactuar con el usuario mediante menús,
 * permitiendo la gestión de socios, libros y préstamos de una
 * biblioteca.
 *
 * Desde esta clase se coordinan las operaciones principales
 * como la creación de socios, incorporación de libros,
 * realización y devolución de préstamos, y consultas generales,
 * delegando la lógica de negocio a la clase Biblioteca.
 * 
 * @author Luciano Pedotti
 * @version 24/12/2025
 */

import java.util.*;
import java.util.Scanner;

public class Main
{
    public static void main (String[] args) {
        int op = -1;
        Scanner sc = new Scanner (System.in);
        Biblioteca biblioteca;
        String nombre;
        
        System.out.println("Ingresar nombre de la biblioteca:");
        nombre = sc.nextLine();
        
        biblioteca = new Biblioteca(nombre);
        
        do{
            try{
                System.out.println("\t\t***MENU PRINCIPAL***\n");
                System.out.println("1 - Menu de Socios");
                System.out.println("2 - Menu de Libros");
                System.out.println("0 - Salir");
                
                op = Integer.parseInt(sc.nextLine());
                
                switch(op){
                    case 1:
                        Main.menuSocios(biblioteca);
                        break;
                    
                    case 2:
                        Main.menuLibros(biblioteca);
                        break;
                        
                    case 0:
                        break;
                        
                    default:
                        System.out.println("Ingrese una opcion valida");
                }
            }catch (NumberFormatException e){
                System.out.println("Error, se debe ingresar un numero entero");
            }
            catch (LibroNoPrestadoException lnpe){
                System.out.println(lnpe.getMessage());
            }
            catch (SocioDuplicadoException sde){
                System.out.println(sde.getMessage());
            }
            catch (SocioNoExistenteException snoee){
                System.out.println(snoee.getMessage());
            }
            catch (LibroNoExistenteException lnee){
                System.out.println(lnee.getMessage());
            }
            catch (LibroPrestadoException lpe){
                System.out.println(lpe.getMessage());
            }
        }while (op != 0);
        
    }

    public static void menuSocios(Biblioteca p_biblioteca) throws SocioDuplicadoException, SocioNoExistenteException {
        int op = -1;
        Scanner sc = new Scanner (System.in);
        do{
            
            System.out.println("\t**MENU SOCIOS**\n");
            System.out.println("1 - Agregar Socio");
            System.out.println("2 - Quitar Socio");
            System.out.println("3 - Cantidad de Socios por tipo");
            System.out.println("4 - Docentes responsables");
            System.out.println("5 - Agregar dias de prestamo a un Docente");
            System.out.println("6 - Lista completa de Socios");
            System.out.println("0 - Menu anterior");
                
            op = Integer.parseInt(sc.nextLine());
                
            switch (op){
                case 1:
                    Main.agregarSocio(p_biblioteca);
                   break;
                       
                case 2:
                    System.out.println("Ingresar Dni del Socio a quitar");
                     int dni = Integer.parseInt(sc.nextLine());
                    if(p_biblioteca.buscarSocio(dni) == null){
                        throw new SocioNoExistenteException();
                    }
                        
                    p_biblioteca.quitarSocio(p_biblioteca.buscarSocio(dni));
                    break;
                case 3:
                    boolean continuar = true;
                    String tipo = null;

                    do{
                        System.out.println("Tipo de Socio :(1- Estudiante / 2- Docente / 3- Menu anterior)");
                        op = Integer.parseInt(sc.nextLine());

                        switch(op){
                            case 1:
                                tipo = "Estudiante";
                                continuar = false;
                                break;
                                
                            case 2:
                                tipo = "Docente";
                                continuar = false;
                                break;
                                    
                            case 3:
                                continuar = false;
                                break;
                                    
                            default :
                                System.out.println("Ingresar un valor valido");
                        }
                    }while (continuar);
                        
                     System.out.println("**Cantidad de Socios de tipo " + tipo + "**");
                    System.out.println(p_biblioteca.cantidadSociosPorTipo(tipo));
                    break;
                        
                case 4:
                        
                    System.out.println(p_biblioteca.listaDeDocentesResponsables());
                    break;
                    
                case 5:
                    System.out.println("Ingresar Dni del Docente");
                    dni = Integer.parseInt(sc.nextLine());
                    if(p_biblioteca.buscarSocio(dni) == null){
                        throw new SocioNoExistenteException();
                    }
                        
                    if(!p_biblioteca.buscarSocio(dni).soyDeLaClase().equalsIgnoreCase("docente")){
                        System.out.println("\t**El dni ingresado no pertenece a un docente**");
                        break;
                    }
                        
                    System.out.println("Ingreasr dias a agregar:");
                    int dias = Integer.parseInt(sc.nextLine());
                    ((Docente)p_biblioteca.buscarSocio(dni)).cambiarDiasDePrestamo(dias);
                        
                    break;
                        
                case 6:
                    System.out.println(p_biblioteca.listaDeSocios());
                    break;
                    
                case 0:
                    break;
                        
                default:
                    System.out.println("**INGRESAR UNA OPCION VALIDA**");
            }
        }while (op != 0);
    }
    
    public static void agregarSocio(Biblioteca p_biblioteca) throws SocioDuplicadoException {
        int op = -1;
        Scanner sc = new Scanner (System.in);
        do{
            System.out.println("1 - Socio Estudiante");
            System.out.println("2 - Socio Docente");
            System.out.println("0 - Menu anterior");
                
            op = Integer.parseInt(sc.nextLine());
                
            switch (op){
                case 1:
                    System.out.println("Ingresar DNI del estudiante");
                    int dni = Integer.parseInt(sc.nextLine());
                        
                    System.out.println("Ingresar Nombre");
                    String nombre = sc.nextLine();
                                
                    System.out.println("Ingresar Carrera");
                    String carrera = sc.nextLine();
                                
                    p_biblioteca.nuevoSocioEstudiante(dni, nombre, carrera);
                                
                    break;
                            
                case 2:
                    System.out.println("Ingresar DNI del Docente");
                    dni = Integer.parseInt(sc.nextLine());
                    
                    System.out.println("Ingresar nombre");
                    nombre = sc.nextLine();
                                
                    System.out.println("Ingresar Area");
                    String area = sc.nextLine();
                                
                    p_biblioteca.nuevoSocioDocente(dni, nombre, area);
                                
                    break;
                                
                case 0:
                    break;
                                
                default:
                    System.out.println("Opcion invalida");
            }
        } while (op != 0);
    }
    
    public static void menuLibros(Biblioteca p_biblioteca) throws LibroPrestadoException, SocioNoExistenteException, LibroNoPrestadoException, LibroNoExistenteException {
        int op = -1;
        Scanner sc = new Scanner (System.in);
        do{
            System.out.println("\t**MENU LIBROS**\n");
            System.out.println("1 - Agregar Libro");
            System.out.println("2 - Quitar Libro");
            System.out.println("3 - Menu de prestamos");
            System.out.println("4 - Listas");
            System.out.println("0 - Menu anterior");
            
            op = Integer.parseInt(sc.nextLine());
            
            switch(op){
                case 1:
                    Main.agregarLibro(p_biblioteca);
                    break;
                
                case 2:
                    System.out.println("Seleccionar libro a quitar");
                    System.out.println(p_biblioteca.listaDeLibros());
                    op = Integer.parseInt(sc.nextLine());
                    
                    if(op < 1 || op > p_biblioteca.getLibros().size()){
                        throw new LibroNoExistenteException();
                    }
                    
                    if(!p_biblioteca.quitarLibro(p_biblioteca.getLibros().get(op -1))){
                        System.out.println("**No se pudo quitar libro**");
                    }
                    
                    break;
                    
                case 3:
                    Main.menuPrestamos(p_biblioteca);
                    break;
                    
                case 4:
                    boolean continuar = true;
                    
                    do{
                        System.out.println("1 - Lista de titulos / 2 - Lista de Libros / 3 - Menu anterior");
                        op = Integer.parseInt(sc.nextLine());
                        switch(op){
                            case 1:
                                System.out.println(p_biblioteca.listaDeTitulos());
                                continuar = false;
                                break;
                            
                            case 2:
                                System.out.println(p_biblioteca.listaDeLibros());
                                continuar = false;
                                break;
                            
                            case 3:
                                continuar = false;
                                break;
                                
                            default:
                                System.out.println("Ingrese una opcion valida");
                        }
                    }while (continuar);
                    
                    break;
                    
                case 0:
                   break;
                   
                default:
                    System.out.println("Ingresar una opcion valida");
            }
        }while (op != 0);
    }
    
    public static void agregarLibro(Biblioteca p_biblioteca){
        int op = -1;
        Scanner sc = new Scanner (System.in);
        
        System.out.println("Ingresar titulo");
        String titulo = sc.nextLine();
        System.out.println("Ingresar edicion");
        int edicion = Integer.parseInt(sc.nextLine());
        System.out.println("Ingresar editorial");
        String editorial = sc.nextLine();
        System.out.println("Ingresar Anio");
        int anio = Integer.parseInt(sc.nextLine());
            
        p_biblioteca.nuevoLibro(titulo, edicion, editorial, anio);
    }
    
    public static void menuPrestamos(Biblioteca p_biblioteca) throws LibroPrestadoException, LibroNoExistenteException, LibroNoPrestadoException, SocioNoExistenteException {
        int op = -1;
        Scanner sc = new Scanner (System.in);
        
        do{
            System.out.println("\t****MENU PRESTAMOS****");
            System.out.println("1- Prestar libro");
            System.out.println("2- Devolver libro");
            System.out.println("3- Consultar prestamo");
            System.out.println("4- Prestamos vencidos");
            System.out.println("0- Menu anterior");
            
            op = Integer.parseInt(sc.nextLine());
            
            switch(op){
                case 1:
                    System.out.println("Seleccionar libro a prestar");
                    System.out.println(p_biblioteca.listaDeLibros());
                    op = Integer.parseInt(sc.nextLine());
                    
                    if(op < 1 || op > p_biblioteca.getLibros().size()){
                        throw new LibroNoExistenteException();
                    }
                    
                    Libro libro = p_biblioteca.getLibros().get(op-1);
                    
                    System.out.println("Seleccionar Socio a quien se hara el prestamo");
                    System.out.println(p_biblioteca.listaDeSocios());
                    op = Integer.parseInt(sc.nextLine());
                    
                    if(op < 1 || op > p_biblioteca.getSocios().size()){
                        throw new LibroNoExistenteException();
                    }
                    
                    Socio socio = p_biblioteca.getSocios().get(op-1);
                    
                    if(!p_biblioteca.prestarLibro(new GregorianCalendar(), socio, libro)){
                        System.out.println("**No se puedo prestar el libro, excedio el limite de prestamos**");
                    }
                    
                    break;
                    
                case 2:
                    System.out.println("Seleccionar libro a devolver");
                    System.out.println(p_biblioteca.listaDeLibros());
                    op = Integer.parseInt(sc.nextLine());
                    
                    if(op < 1 || op > p_biblioteca.getLibros().size()){
                        throw new LibroNoExistenteException();
                    }
                    
                    p_biblioteca.devolverLibro(p_biblioteca.getLibros().get(op-1));
                    
                    break;
                    
                case 3:
                    System.out.println("Seleccionar libro");
                    System.out.println(p_biblioteca.listaDeLibros());
                    op = Integer.parseInt(sc.nextLine());
                    
                    if(op < 1 || op > p_biblioteca.getLibros().size()){
                        throw new LibroNoExistenteException();
                    }
                    
                    System.out.println("El libro " + p_biblioteca.getLibros().get(op -1).getTitulo() + " lo tiene " + 
                      p_biblioteca.quienTieneElLibro(p_biblioteca.getLibros().get(op-1)));
                      
                    break;
                    
                case 4:
                    if(p_biblioteca.prestamosVencidos().isEmpty()){
                        System.out.println("**No hay prestamos vencidos**");
                        break;
                    }
                    
                    for(Prestamo unPrestamo : p_biblioteca.prestamosVencidos()){
                        unPrestamo.toString();
                    }
                    
                    break;
                
                case 0:
                    break;
                
                default:
                    System.out.println("Ingresar una opcion valida");
            }
        }while (op != 0);
    }
    
    
}