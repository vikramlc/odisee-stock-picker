package app.odisee.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

class BudgetBundleTest {

    private static Item item(String sku, long price) {
        return new Item(sku, sku, price, 1);
    }

    @Test
    void picksHighestTotalWithinBudget() {
        List<Item> items = List.of(item("A", 450), item("B", 1500), item("C", 100), item("D", 800), item("E", 400));
        assertEquals(OptionalLong.of(900), BudgetBundle.bestPairTotal(items, 950));
    }

    @Test
    void emptyWhenNothingFits() {
        List<Item> items = List.of(item("A", 800), item("B", 900), item("C", 700));
        assertEquals(OptionalLong.empty(), BudgetBundle.bestPairTotal(items, 1000));
    }

    @Test
    void needsTwoItems() {
        assertEquals(OptionalLong.empty(), BudgetBundle.bestPairTotal(List.of(item("A", 10)), 1000));
    }

    @Test
    void adjacentPairAtTheEndIsConsidered() {
        assertEquals(OptionalLong.of(30), BudgetBundle.bestPairTotal(List.of(item("A", 10), item("B", 20)), 30));
    }
}
