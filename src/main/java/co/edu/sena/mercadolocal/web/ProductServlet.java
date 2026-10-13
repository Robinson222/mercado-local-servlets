package co.edu.sena.mercadolocal.web;

import co.edu.sena.mercadolocal.model.Product;
import co.edu.sena.mercadolocal.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/** Serves the catalog with GET and processes HTML form CRUD actions with POST. */
@WebServlet(urlPatterns = {"/products", "/"})
public final class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService service;

    @Override
    public void init() throws ServletException {
        Object value = getServletContext().getAttribute(ApplicationListener.SERVICE_KEY);
        if (!(value instanceof ProductService)) {
            throw new ServletException("No se inicializo ProductService.");
        }
        service = (ProductService) value;
    }

    /** GET displays products and consumes a one-time message after a POST redirect. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object message = session.getAttribute("flashMessage");
            if (message != null) {
                request.setAttribute("flashMessage", message);
                session.removeAttribute("flashMessage");
            }
        }
        render(request, response, null, HttpServletResponse.SC_OK);
    }

    /** POST validates a form action, runs it in the service, then redirects to GET. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!sameOrigin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origen no permitido.");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                Product saved = service.create(request.getParameter("name"),
                        request.getParameter("description"), request.getParameter("price"),
                        request.getParameter("stock"));
                flash(request, "Producto creado con id " + saved.getId() + ".");
            } else if ("update".equals(action)) {
                boolean updated = service.update(request.getParameter("id"),
                        request.getParameter("name"), request.getParameter("description"),
                        request.getParameter("price"), request.getParameter("stock"));
                flash(request, updated ? "Producto actualizado." : "No se encontro el producto.");
            } else if ("delete".equals(action)) {
                boolean deleted = service.delete(request.getParameter("id"));
                flash(request, deleted ? "Producto eliminado." : "No se encontro el producto.");
            } else {
                render(request, response, "La accion no es valida.", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            response.sendRedirect(request.getContextPath() + "/products");
        } catch (IllegalArgumentException exception) {
            render(request, response, exception.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException exception) {
            getServletContext().log("Error ejecutando operacion JDBC de productos.", exception);
            render(request, response, "No se pudo acceder a MySQL. Revise la configuracion DB_*.",
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void render(HttpServletRequest request, HttpServletResponse response,
                        String error, int status) throws ServletException, IOException {
        try {
            List<Product> products = service.findAll();
            request.setAttribute("products", products);
        } catch (SQLException exception) {
            getServletContext().log("No se pudo consultar el catalogo.", exception);
            request.setAttribute("products", Collections.emptyList());
            if (error == null) {
                error = "No se pudo cargar el catalogo; revise la conexion a MySQL.";
                status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }
        }
        request.setAttribute("errorMessage", error);
        response.setStatus(status);
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    private static void flash(HttpServletRequest request, String message) {
        request.getSession().setAttribute("flashMessage", message);
    }

    /** Rejects cross-site browser POSTs when the request has an Origin header. */
    private static boolean sameOrigin(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        if (origin == null || origin.isEmpty()) {
            return true;
        }
        String host = request.getHeader("Host");
        return host != null && origin.equals(request.getScheme() + "://" + host);
    }
}
