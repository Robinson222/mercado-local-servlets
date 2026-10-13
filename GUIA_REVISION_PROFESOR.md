# Guia de revision docente - GA7 AA2 EV02

## Archivos clave

1. Abra `README.md` para conocer tecnologias, configuracion de MySQL y despliegue.
2. Revise `src/main/java/.../web/ProductServlet.java`: `doGet` consulta y `doPost` maneja formularios.
3. Revise `src/main/webapp/WEB-INF/views/products.jsp`: formularios HTML y expresiones JSP/JSTL.
4. Revise `repository/JdbcProductRepository.java`: SQL parametrizado para consultar, insertar, actualizar y eliminar.
5. Revise `service/ProductService.java` y `src/test/.../ProductServiceTest.java`: validaciones y pruebas automatizadas del servicio.
6. Use `database/schema.sql` para crear base y tabla, y asignar privilegios minimos.

## Ejecutar desde cero

1. Instale JDK 11+, Maven 3.8+, MySQL 8.4+ y Tomcat 10.1.
2. Ejecute `database/schema.sql` en MySQL. Cree `mercado_app` con los permisos SELECT/INSERT/UPDATE/DELETE y configure su clave.
3. En PowerShell, defina DB_URL, DB_USER y DB_PASSWORD como se muestra en README.
4. Desde la carpeta del proyecto, ejecute `mvn clean test package`.
5. Copie `target/mercado-local-servlets.war` a `<TOMCAT_HOME>\webapps` e inicie Tomcat.
6. Abra `http://localhost:8080/mercado-local-servlets/products`.
7. Pruebe alta, consulta/recarga, actualizacion y eliminacion desde los formularios.

## Pruebas

`mvn test` ejecuta las pruebas unitarias del servicio con una implementacion en memoria, sin tocar MySQL. La capa JDBC debe probarse desplegando la aplicacion con la base configurada. Esta entrega incluye el codigo de pruebas, pero no se pudieron ejecutar en el entorno de preparacion por falta de JDK/Maven/Tomcat/MySQL.

## Repositorio

El paquete contiene `Mercado_Local_Servlets.gitbundle` para recuperar el historial local: `git clone Mercado_Local_Servlets.gitbundle`. El archivo `ENLACE_REPOSITORIO.txt` indica el estado del URL remoto, separado del repositorio Flask de la evidencia anterior.
