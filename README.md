# Mercado Local - Servlets y JSP

Proyecto web Java empaquetado como WAR. Permite listar, crear, actualizar y eliminar productos mediante formularios HTML, Servlets GET/POST, JSP/JSTL y persistencia JDBC con MySQL.

## Tecnologias y versiones

- JDK 11 o superior.
- Apache Tomcat 10.1 (Servlet 6.0 y JSP 3.1).
- Maven 3.8 o superior.
- MySQL 8.4 o superior.
- Jakarta JSTL 3.0.1 y MySQL Connector/J 26.7.0.

Tomcat 10.1 requiere Java 11+ y soporta Jakarta Servlet 6.0 y Pages/JSP 3.1. Esta version usa paquetes `jakarta.servlet`, por lo que debe desplegarse en Tomcat 10.1 y no Tomcat 9.

## Estructura por capas

- `web/ProductServlet.java`: maneja `doGet` para consulta y `doPost` para acciones de formularios.
- `web/ApplicationListener.java`: registra el servicio al iniciar el contexto web.
- `service/ProductService.java`: valida datos y coordina operaciones CRUD.
- `repository/JdbcProductRepository.java`: acceso JDBC con `PreparedStatement` y try-with-resources.
- `config/DatabaseConnection.java`: lee conexion y secretos desde variables de entorno.
- `model/Product.java`: entidad de dominio y validacion basica.
- `src/main/webapp/WEB-INF/views/products.jsp`: formulario de alta, edicion, borrado y listado con JSTL.
- `database/schema.sql`: tabla y ejemplo de permisos minimos.

## Base de datos

1. Inicie MySQL y ejecute `database/schema.sql` con un usuario administrador.
2. Cambie `CAMBIAR_POR_CLAVE_SEGURA`, quite comentarios y ejecute `CREATE USER`, `GRANT` y `FLUSH PRIVILEGES` una vez.
3. Configure variables en PowerShell para la sesion actual:

```powershell
$env:DB_URL = 'jdbc:mysql://localhost:3306/mercado_local?serverTimezone=UTC'
$env:DB_USER = 'mercado_app'
$env:DB_PASSWORD = 'la-clave-configurada-en-mysql'
```

No almacene credenciales en Git. Para despliegue real, use TLS y un gestor de secretos.

## Compilar y desplegar

En la carpeta que contiene `pom.xml`:

```powershell
mvn clean test package
```

El archivo WAR se genera como `target/mercado-local-servlets.war`. Copielo a `<TOMCAT_HOME>\webapps` e inicie Tomcat 10.1. Abra:

`http://localhost:8080/mercado-local-servlets/products`

## Rutas y formularios

- `GET /products`: lista los productos usando JSP.
- Formulario `POST /products`, `action=create`: inserta un producto.
- Formulario `POST /products`, `action=update`: actualiza producto por ID.
- Formulario `POST /products`, `action=delete`: elimina producto por ID.

Despues de una escritura exitosa se aplica Post/Redirect/Get y aparece un mensaje de confirmacion. Los errores de validacion se muestran en la pagina.

## Pruebas

`src/test/.../ProductServiceTest.java` contiene pruebas unitarias con un repositorio en memoria para alta, normalizacion, validacion, actualizacion y eliminacion. Ejecutelas con `mvn test`. La integracion JDBC requiere MySQL y se verifica manualmente desde la interfaz.

## Seguridad y alcance

Las consultas SQL son parametrizadas; JSTL `c:out` escapa datos mostrados; hay validacion de origen en POST y la cookie de sesion es HttpOnly. Es una aplicacion academica sin autenticacion. No la exponga a Internet sin autenticacion/autorizacion, token CSRF, HTTPS y configuracion de produccion.

## Versionamiento

El codigo esta en Git, rama `main`. La raiz incluye `ENLACE_REPOSITORIO.txt` y el paquete de entrega contiene el Git bundle portable. El repositorio remoto de esta evidencia debe publicarse por separado; no reutilice el remoto de la aplicacion Flask.

## Estado de compilacion en esta entrega

Se incluyeron las pruebas, pero no fue posible compilarlas ni ejecutarlas en el entorno de elaboracion porque faltan JDK 11+, Maven, Tomcat y MySQL. La guia `GUIA_REVISION_PROFESOR.md` contiene los pasos de revision y ejecucion.

## Referencias oficiales

- Apache Tomcat 10.1: https://tomcat.apache.org/tomcat-10.1-doc/
- Requisitos Tomcat 10.1: https://tomcat.apache.org/tomcat-10.1-doc/appdev/installation.html
- Guia de migracion y APIs: https://tomcat.apache.org/migration-10.1.html
