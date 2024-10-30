# CRUD Coches con MongoDB
Este proyecto es una aplicación de escritorio desarrollada en **Java** utilizando **JavaFX** que permite realizar un CRUD (Crear, Leer, Actualizar, Eliminar) de coches, almacenando los datos en una base de datos **MongoDB**.

## Funcionalidades

- Agregar y visualizar coches.
- Editar y eliminar coches.
- Conectar y desconectar de la base de datos MongoDB.

## Tecnologías Utilizadas

- Java 23
- JavaFX
- MongoDB
- Maven

## Estructura del Proyecto

- **src/**: Contiene el código fuente del proyecto.
   - **acceso.dam.mongocrud_acilleruelosinovas.Controller**: Clases que manejan la lógica de la interfaz de usuario.
   - **acceso.dam.mongocrud_acilleruelosinovas.Utils**: Clases utilitarias para manejar alertas, conexión a la base de datos y recursos.
   - **acceso.dam.mongocrud_acilleruelosinovas.Model**: Clases que representan las entidades del sistema (Coche), coincidentes con las colecciones de la base de datos MongoDB.
- **resources/**: Carpeta que contiene las imágenes y archivos FXML utilizados en la interfaz de usuario.
   - **images/**: Carpeta con los íconos y recursos gráficos.
   - **ui/**: Archivos FXML para las vistas de la aplicación.
   - **configuration/**: Archivos de configuración del proyecto, como `database.properties`.

## Instalación

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/alvarocille/CRUDCoches-MongoDB.git
2. Abre el proyecto en un IDE (IntelliJ Idea o VSCode).
3. Compila el proyecto y ejecuta la clase Main.

## Cambios Pendientes
- Implementar la funcionalidad de búsqueda avanzada de coches.
- Mejorar la gestión de errores y manejo de excepciones.
- Optimizar la interfaz gráfica para adaptarse a diferentes resoluciones de pantalla.

## Autor

Álvaro Cilleruelo Sinovas   
alvaro@cillesino.com
