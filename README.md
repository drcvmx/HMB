# Proyecto HMB (How Many Burgers) - Juego de Adivinanzas con Sesiones y Base de Datos PostgreSQL

Este proyecto es una aplicación web Java que implementa un juego de adivinanzas ("How Many Burgers"). Permite a los jugadores registrarse, iniciar sesión, jugar, almacenar y consultar su récord personal, así como ver el top 10 de las mejores puntuaciones. La aplicación utiliza Servlets y JSP para la interfaz de usuario y la lógica del servidor, y persiste los datos en una base de datos PostgreSQL.

## Arquitectura del Sistema

**Nuestro sistema utiliza una arquitectura Cliente-Servidor.** El servidor está diseñado en **4 capas**, las cuales son: Presentación, Control, Lógica de Negocio y Persistencia. Se utiliza el **patrón DAO (Data Access Object)** para la capa de persistencia y **PostgreSQL** como sistema de gestión de bases de datos.

## Funcionalidades Implementadas

El sistema **permite a los jugadores** realizar las siguientes acciones:

*   **Jugar How Many Burgers**: Los jugadores pueden participar en el juego de adivinar un número secreto.
*   **Almacenar y Consultar su Récord Personal**: Una vez logueados, los jugadores pueden guardar su mejor puntuación (el menor número de intentos) y la fecha en que lo lograron. También pueden consultar su récord personal en cualquier momento.
*   **Consultar el Top 10 de las Mejores Puntuaciones**: Se proporciona una tabla con las 10 mejores puntuaciones globales del juego, visible para todos los usuarios.
*   **Registro de Usuarios**: Permite a los nuevos usuarios crear una cuenta con nombre de usuario, contraseña y correo electrónico (opcional).
*   **Inicio de Sesión**: Los usuarios registrados deben autenticarse antes de poder acceder a las funcionalidades del juego y récords personales.
*   **Gestión de Sesiones**: El estado del usuario (logueado, número secreto, intentos) se mantiene a través de sesiones HTTP.
*   **Cierre de Sesión**: Permite a los usuarios cerrar su sesión de forma segura.
*   **Persistencia de Datos**: Usuarios y récords se almacenan en una base de datos PostgreSQL.

## Requisitos Previos

Para poder usar y desplegar este proyecto en cualquier equipo, necesitarás lo siguiente:

1.  **Java Development Kit (JDK)**: Versión 11 o superior.
2.  **Apache Maven**: Para compilar y empaquetar el proyecto.
3.  **Apache Tomcat**: Versión 10.x o superior (se ha utilizado 11.0.11 en el desarrollo).
4.  **PostgreSQL**: Un servidor de base de datos PostgreSQL.
5.  **Cliente psql**: Para interactuar con la base de datos desde la línea de comandos.

## Pasos para Configurar y Desplegar el Proyecto

Sigue estos pasos cuidadosamente para poner en marcha la aplicación:

### 1. Configurar la Base de Datos PostgreSQL

Este proyecto utiliza una base de datos PostgreSQL llamada `howmanyburgers_db`.

*   **1.1. Configuración del Pool de Conexiones JNDI en Tomcat (Crucial para Cualquier Equipo):**
    La conexión a la base de datos es gestionada por un pool de conexiones configurado directamente en Apache Tomcat, utilizando JNDI (Java Naming and Directory Interface). Esto significa que **los detalles de la conexión (URL, usuario, contraseña) no se especifican directamente en el código de la aplicación (HMB/src/main/java/com/juego/howmanyburgers/util/DBUtil.java), sino en el archivo de configuración de Tomcat.**

    **Paso a Paso para la Conexión:**

    1.  **Localiza el `context.xml` de Tomcat:**
        Este archivo se encuentra en la ruta de instalación de tu Tomcat:
        `C:\ruta\a\tu\apache-tomcat-11.0.11\conf\context.xml`
        (Reemplaza `C:\ruta\a\tu\apache-tomcat-11.0.11` con la ruta real de tu instalación de Tomcat).

    2.  **Edita el archivo `context.xml`:**
        Abre este archivo con un editor de texto y busca la etiqueta `<Context>`. Dentro de esta etiqueta, añade la siguiente configuración `Resource`:

        ```xml
        <!-- Configuración del DataSource para el pool de conexiones de PostgreSQL -->
        <Resource name="jdbc/HowManyBurgersDB" auth="Container" type="javax.sql.DataSource"
                   factory="org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory"
                   driverClassName="org.postgresql.Driver"
                   url="jdbc:postgresql://localhost:5432/howmanyburgers_db"
                   username="postgres"
                   password="TU_CONTRASEÑA_POSTGRESQL" <!-- ¡IMPORTANTE! REEMPLAZA CON LA CONTRASEÑA REAL DE TU USUARIO 'postgres' -->
                   maxTotal="20"
                   maxIdle="10"
                   maxWaitMillis="10000" /> <!-- Tiempo de espera en ms para obtener una conexión -->
        ```
        **Asegúrate de reemplazar `TU_CONTRASEÑA_POSTGRESQL` con la contraseña real de tu usuario `postgres` en tu base de datos PostgreSQL local.** Si tu usuario `postgres` no tiene contraseña, usa `password=""` (cadena vacía). La URL `jdbc:postgresql://localhost:5432/howmanyburgers_db` asume que PostgreSQL está corriendo en el mismo equipo y puerto por defecto.

*   **1.2. Crear la Base de Datos:**
    Abre tu terminal (PowerShell en Windows, Bash en Linux/macOS) y ejecuta el siguiente comando para crear la base de datos. Asegúrate de sustituir `[TU_CONTRASEÑA_POSTGRES]` con la contraseña de tu usuario `postgres`.

    ```bash
    psql -U postgres -c "CREATE DATABASE howmanyburgers_db;"
    ```

*   **1.3. Crear el Esquema de la Base de Datos:**
    Ahora, ejecuta el script SQL que define las tablas `usuarios`, `juegos` y `records`, e inserta el juego "HowManyBurgers".

    **Asumiendo que el archivo `database.sql` se encuentra en el directorio `How_manyBurgersBd`:**
    ```bash
    psql -U postgres -d howmanyburgers_db -f "C:\ruta\al\directorio\How_manyBurgersBd\database.sql"
    ```
    (Reemplaza `C:\ruta\al\directorio\How_manyBurgersBd` con la ruta real donde tengas el material de `How_manyBurgersBd`).
    Introduce la contraseña de tu usuario `postgres` si te la solicita.

    **Verificación (Opcional):** Para asegurarte de que las tablas se crearon, puedes conectarte a la base de datos y listar las tablas:
    ```bash
    psql -U postgres -d howmanyburgers_db
    \dt
    \q
    ```

### 2. Compilar y Empaquetar la Aplicación

Una vez configurada la base de datos y el archivo `context.xml` de Tomcat, compila y empaqueta el proyecto en un archivo `.war`.

*   **2.1. Navega al Directorio del Proyecto:**
    Abre tu terminal y navega a la raíz del proyecto `HMB` (donde está `pom.xml`):
    ```bash
    cd C:\ruta\a\tu\proyecto\HMB
    ```
    (Reemplaza `C:\ruta\a\tu\proyecto\HMB` con la ruta real).

*   **2.2. Ejecuta Maven para Construir el `.war`:**
    ```bash
    mvn clean install
    ```
    Este comando compilará el código, ejecutará las fases de empaquetado y creará el archivo `HMB.war` en el directorio `HMB/target/`.

### 3. Desplegar el Driver JDBC de PostgreSQL en Tomcat

Para evitar el error "No suitable driver found", es crucial que el driver JDBC de PostgreSQL esté disponible directamente para Tomcat.

*   **3.1. Localiza el JAR del Driver de PostgreSQL:**
    Después de ejecutar `mvn clean install`, Maven habrá descargado el driver. La ruta típica es:
    `C:\Users\[Tu_Usuario]\.m2\repository\org\postgresql\postgresql\42.5.0\postgresql-42.5.0.jar`
    (Asegúrate de que `[Tu_Usuario]` sea tu nombre de usuario de Windows y que la versión `42.5.0` coincida con la de tu `pom.xml`).

*   **3.2. Copia el JAR al Directorio `lib` de Tomcat:**
    Copia el archivo `postgresql-42.5.0.jar` (o la versión que encontraste) a la carpeta `lib` de tu instalación de Tomcat:
    `C:\ruta\a\tu\apache-tomcat-11.0.11\lib\`
    (Reemplaza con la ruta real de tu instalación de Tomcat).

### 4. Desplegar la Aplicación en Tomcat

Ahora que tienes el `.war` y el driver en su lugar, despliega tu aplicación.

*   **4.1. Copia el Archivo `.war` a `webapps` de Tomcat:**
    Copia el `HMB.war` (de `HMB/target/`) al directorio `webapps` de tu instalación de Tomcat:
    `C:\ruta\a\tu\apache-tomcat-11.0.11\webapps\`

### 5. Iniciar (o Reiniciar) Tomcat

Es fundamental reiniciar Tomcat para que recoja tanto el nuevo driver JDBC como tu aplicación.

*   **5.1. Navega al Directorio `bin` de Tomcat:**
    `C:\ruta\a\tu\apache-tomcat-11.0.11\bin\`

*   **5.2. Detener Tomcat (si está ejecutándose):**
    ```bash
    .\shutdown.bat
    ```
    Espera unos segundos hasta que veas el mensaje de que el servidor se ha detenido.

*   **5.3. Iniciar Tomcat:**
    ```bash
    .\startup.bat
    ```
    Verás la ventana de la consola de Tomcat. Espera a que muestre mensajes como `INFO: Server startup in [XXX] milliseconds` para confirmar que se ha iniciado completamente.

### 6. Acceder a la Aplicación en el Navegador

Una vez que Tomcat esté ejecutándose y tu aplicación desplegada, puedes acceder a ella.

*   **6.1. Abre tu Navegador Web:**
    En la barra de direcciones, escribe la siguiente URL:
    ```
    http://localhost:8080/HMB/
    ```

### 7. Probar las Funcionalidades

Desde la página de inicio de tu aplicación:

*   **Registra un nuevo usuario:** Haz clic en "Registrarse", introduce un nombre de usuario, contraseña y correo (opcional).
*   **Inicia sesión:** Usa las credenciales del usuario que acabas de registrar.
*   **Juega al juego:** Adivina el número y observa cómo se registra tu récord.
*   **Consulta tu récord:** Haz clic en "Ver mi Récord".
*   **Consulta el Top 10:** Explora la opción para ver las mejores puntuaciones.
*   **Cierra sesión.**
