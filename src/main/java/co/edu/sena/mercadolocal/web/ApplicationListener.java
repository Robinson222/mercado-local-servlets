package co.edu.sena.mercadolocal.web;

import co.edu.sena.mercadolocal.repository.JdbcProductRepository;
import co.edu.sena.mercadolocal.service.ProductService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/** Registers the application service when Tomcat starts the web context. */
@WebListener
public final class ApplicationListener implements ServletContextListener {
    public static final String SERVICE_KEY = "productService";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ProductService service = new ProductService(new JdbcProductRepository());
        event.getServletContext().setAttribute(SERVICE_KEY, service);
    }
}
