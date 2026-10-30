package app.odisee.stock;

import java.util.Objects;

/** A stock-keeping unit with its unit price and quantity on hand. */
public record Item(String sku, String name, long priceCents, int quantity) {
    public Item {
        Objects.requireNonNull(sku, "sku");
        Objects.requireNonNull(name, "name");
        if (priceCents < 0) {
            throw new IllegalArgumentException("priceCents must be >= 0");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must be >= 0");
        }
    }

    public boolean inStock() {
        return quantity > 0;
    }

    public Item withQuantity(int newQuantity) {
        return new Item(sku, name, priceCents, newQuantity);
    }
}
