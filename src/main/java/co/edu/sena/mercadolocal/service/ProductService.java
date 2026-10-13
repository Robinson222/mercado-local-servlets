package co.edu.sena.mercadolocal.service;

import co.edu.sena.mercadolocal.model.Product;
import co.edu.sena.mercadolocal.repository.ProductRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/** Validates form data and coordinates product CRUD with the repository. */
public final class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("El repositorio es obligatorio.");
        }
        this.repository = repository;
    }

    public List<Product> findAll() throws SQLException {
        return repository.findAll();
    }

    public Product create(String name, String description, String price, String stock) throws SQLException {
        Product product = buildProduct(0, name, description, price, stock);
        return repository.insert(product);
    }

    public boolean update(String id, String name, String description, String price, String stock)
            throws SQLException {
        Product product = buildProduct(parseId(id), name, description, price, stock);
        return repository.update(product);
    }

    public boolean delete(String id) throws SQLException {
        return repository.delete(parseId(id));
    }

    private static Product buildProduct(long id, String name, String description,
                                        String priceText, String stockText) {
        try {
            BigDecimal price = new BigDecimal(requireText(priceText, "El precio es obligatorio."));
            int stock = Integer.parseInt(requireText(stockText, "El inventario es obligatorio."));
            return new Product(id, name, description, price, stock);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Precio o inventario con formato invalido.");
        }
    }

    private static long parseId(String text) {
        try {
            long id = Long.parseLong(requireText(text, "El id es obligatorio."));
            if (id <= 0) {
                throw new IllegalArgumentException("El id debe ser mayor que cero.");
            }
            return id;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El id no es valido.");
        }
    }

    private static String requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
