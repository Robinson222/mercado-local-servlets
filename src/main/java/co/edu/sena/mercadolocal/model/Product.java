package co.edu.sena.mercadolocal.model;

import java.math.BigDecimal;

/** Immutable product entity shared by web, service, and persistence layers. */
public final class Product {
    private final long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final int stock;

    public Product(long id, String name, String description, BigDecimal price, int stock) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo.");
        }
        if (name == null || name.trim().isEmpty() || name.trim().length() > 120) {
            throw new IllegalArgumentException("El nombre es obligatorio y admite hasta 120 caracteres.");
        }
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("La descripcion admite hasta 500 caracteres.");
        }
        if (price == null || price.signum() < 0 || price.scale() > 2 || price.precision() > 12) {
            throw new IllegalArgumentException("El precio no puede ser negativo ni superar dos decimales.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El inventario no puede ser negativo.");
        }
        this.id = id;
        this.name = name.trim();
        this.description = description == null ? "" : description.trim();
        this.price = price;
        this.stock = stock;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", stock=" + stock + "}";
    }
}
