package app.odisee.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** In-memory inventory keyed by SKU. Not thread-safe. */
public final class Inventory {

    private final Map<String, Item> items = new HashMap<>();

    public void put(Item item) {
        items.put(item.sku(), item);
    }

    public Optional<Item> find(String sku) {
        return Optional.ofNullable(items.get(sku));
    }

    /** Removes {@code count} units; throws if the SKU is unknown or stock is short. */
    public Item take(String sku, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must be >= 1");
        }
        Item item = items.get(sku);
        if (item == null) {
            throw new IllegalArgumentException("unknown sku " + sku);
        }
        if (item.quantity() < count) {
            throw new IllegalStateException("only " + item.quantity() + " left of " + sku);
        }
        Item updated = item.withQuantity(item.quantity() - count);
        items.put(sku, updated);
        return updated;
    }

    /** Items with quantity > 0, in no particular order. */
    public List<Item> inStock() {
        List<Item> out = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.inStock()) {
                out.add(item);
            }
        }
        return out;
    }

    public int size() {
        return items.size();
    }
}
