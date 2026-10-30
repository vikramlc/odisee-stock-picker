package app.odisee.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InventoryTest {

    private Inventory inventory() {
        Inventory inv = new Inventory();
        inv.put(new Item("MUG-01", "Mug", 899, 4));
        inv.put(new Item("TEA-02", "Green tea", 450, 0));
        inv.put(new Item("PEN-03", "Pen", 199, 10));
        return inv;
    }

    @Test
    void takeReducesQuantity() {
        Inventory inv = inventory();
        assertEquals(1, inv.take("MUG-01", 3).quantity());
        assertEquals(1, inv.find("MUG-01").orElseThrow().quantity());
    }

    @Test
    void takeMoreThanStockFails() {
        assertThrows(IllegalStateException.class, () -> inventory().take("MUG-01", 5));
    }

    @Test
    void takeUnknownSkuFails() {
        assertThrows(IllegalArgumentException.class, () -> inventory().take("NOPE", 1));
    }

    @Test
    void inStockSkipsEmptyItems() {
        assertEquals(2, inventory().inStock().size());
        assertTrue(inventory().inStock().stream().allMatch(Item::inStock));
    }

    @Test
    void rejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Item("X", "x", -1, 1));
    }
}
