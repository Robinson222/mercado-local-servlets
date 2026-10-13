package co.edu.sena.mercadolocal.repository;

import co.edu.sena.mercadolocal.config.DatabaseConnection;
import co.edu.sena.mercadolocal.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** MySQL repository. PreparedStatement binds values and prevents SQL injection. */
public final class JdbcProductRepository implements ProductRepository {
    private static final String COLUMNS = "id, name, description, price, stock";

    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM products ORDER BY name, id";
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                products.add(mapProduct(result));
            }
        }
        return products;
    }

    @Override
    public Product insert(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindProduct(statement, product);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("MySQL no retorno el identificador generado.");
                }
                return new Product(keys.getLong(1), product.getName(), product.getDescription(),
                        product.getPrice(), product.getStock());
            }
        }
    }

    @Override
    public boolean update(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindProduct(statement, product);
            statement.setLong(5, product.getId());
            return statement.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    private static void bindProduct(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setBigDecimal(3, product.getPrice());
        statement.setInt(4, product.getStock());
    }

    private static Product mapProduct(ResultSet result) throws SQLException {
        return new Product(result.getLong("id"), result.getString("name"),
                result.getString("description"), result.getBigDecimal("price"), result.getInt("stock"));
    }
}
