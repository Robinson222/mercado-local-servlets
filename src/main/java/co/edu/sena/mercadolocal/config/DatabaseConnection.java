package co.edu.sena.mercadolocal.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Creates JDBC connections using environment configuration instead of source secrets. */
public final class DatabaseConnection {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/mercado_local?serverTimezone=UTC";

    private DatabaseConnection() {
        // Utility class.
    }

    public static Connection open() throws SQLException {
        String url = environmentOrDefault("DB_URL", DEFAULT_URL);
        String user = environmentOrDefault("DB_USER", "mercado_app");
        String password = environmentOrDefault("DB_PASSWORD", "");
        if (password.isEmpty()) {
            throw new SQLException("Configure DB_PASSWORD para conectar con MySQL.");
        }
        return DriverManager.getConnection(url, user, password);
    }

    private static String environmentOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }
}
