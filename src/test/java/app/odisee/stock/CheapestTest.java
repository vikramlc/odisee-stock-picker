package app.odisee.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class CheapestTest {

    @Test
    void cheapestInStockWithTies() {
        Inventory inv = new Inventory();
        inv.put(new Item("B", "b", 300, 1));
        inv.put(new Item("A", "a", 300, 1));
        inv.put(new Item("C", "c", 100, 0));
        inv.put(new Item("D", "d", 900, 2));
        assertEquals(List.of("A", "B"), inv.cheapest(2).stream().map(Item::sku).toList());
        assertEquals(3, inv.cheapest(10).size());
        assertEquals(List.of(), inv.cheapest(0));
    }
}
