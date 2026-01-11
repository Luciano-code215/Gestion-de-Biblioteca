# Sistema de Gestión de Biblioteca

Este es un sistema de gestión de biblioteca desarrollado en **Java SE** con persistencia en **SQLite**. Permite gestionar socios (Docentes y Alumnos), libros y préstamos con sincronización en tiempo real entre la memoria RAM y la base de datos.
Este sistema fue creado con fines educativos.

## Funcionalidades
* **Gestión de Socios**: Alta, baja y modificación de Alumnos y Docentes.
* **Control de Libros**: Registro de libros y consulta de disponibilidad.
* **Préstamos y Devoluciones**: Registro de salida de libros y actualización de fecha de devolución.
* **Persistencia**: Todos los datos se guardan en una base de datos local SQLite.

## Requisitos e Instalación

### Requisitos previos
* **Java JDK 8** o superior.
* **SQLite JDBC Driver** (incluido en la carpeta `/lib`).

### Instalación
1. Clona el repositorio:
   ```bash
   git clone [https://github.com/tu-usuario/tu-repositorio.git](https://github.com/tu-usuario/tu-repositorio.git)
   
2. Abre el proyecto en tu IDE favorito (NetBeans, IntelliJ, Eclipse).

3. Importante: Asegúrate de agregar el archivo `sqlite-jdbc-3.51.1.0.jar` (ubicado en la carpeta lib) a las librerías del proyecto (Build Path).

4. Ejecuta la clase Main.java.

##Autor : Luciano Pedotti
