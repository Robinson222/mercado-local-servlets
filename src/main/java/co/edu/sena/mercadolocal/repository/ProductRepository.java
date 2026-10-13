package co.edu.sena.mercadolocal.repository;

import co.edu.sena.mercadolocal.model.Product;
import java.sql.SQLException;
import java.util.List;

/** Data-access contract for the product module. */
public interface ProductRepository {
    List<Product> findAll() throws SQLException;
    Product insert(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(long id) throws SQLException;
}
