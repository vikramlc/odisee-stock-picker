package app.odisee.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Hidden verifier for Task 2 (fail-to-pass). Do not read before solving. */
class T2CheapestVerifierTest {

    private static Inventory inventory() {
        Inventory inv = new Inventory();
        inv.put(new Item("MUG-01", "Mug", 899, 4));
        inv.put(new Item("TEA-02", "Green tea", 450, 0)); // out of stock
        inv.put(new Item("PEN-03", "Pen", 199, 10));
        inv.put(new Item("CUP-04", "Cup", 450, 2));
        inv.put(new Item("BAG-05", "Bag", 450, 1));
        inv.put(new Item("HAT-06", "Hat", 1200, 3));
        return inv;
    }

    private static List<String> skus(List<Item> items) {
        return items.stream().map(Item::sku).toList();
    }

    @Test
    void cheapestFirstWithSkuTieBreak() {
        assertEquals(List.of("PEN-03", "BAG-05", "CUP-04"), skus(inventory().cheapest(3)));
    }

    @Test
    void skipsOutOfStock() {
        assertTrue(skus(inventory().cheapest(10)).stream().noneMatch("TEA-02"::equals));
    }

    @Test
    void kLargerThanStockReturnsAllSorted() {
        assertEquals(List.of("PEN-03", "BAG-05", "CUP-04", "MUG-01", "HAT-06"), skus(inventory().cheapest(99)));
    }

    @Test
    void kZeroOrNegativeIsEmpty() {
        assertEquals(List.of(), inventory().cheapest(0));
        assertEquals(List.of(), inventory().cheapest(-1));
    }

    @Test
    void largeInventoryIsFastAndCorrect() {
        Inventory inv = new Inventory();
        for (int i = 0; i < 300_000; i++) {
            inv.put(new Item(String.format("SKU-%06d", i), "x", (i * 7919L) % 100_003, 1));
        }
        long start = System.nanoTime();
        List<Item> top = inv.cheapest(5);
        long ms = (System.nanoTime() - start) / 1_000_000;
        assertTrue(ms < 2000, "took " + ms + " ms");
        assertEquals(5, top.size());
        for (int i = 1; i < top.size(); i++) {
            assertTrue(top.get(i - 1).priceCents() <= top.get(i).priceCents());
        }
        assertEquals(0, top.get(0).priceCents());
    }
}
