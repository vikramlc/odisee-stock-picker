package app.odisee.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

/**
 * Bug report #7 from the storefront team: with a 12.00 budget the bundle picker
 * offers a 8.00 bundle although the 5.00 + 6.50 bundle fits. Fails on main.
 */
class BudgetBundleBugReportTest {

    @Test
    void findsTheTwoMostExpensiveItemsWhenTheyFit() {
        List<Item> items = List.of(
                new Item("CARD-1", "Card", 150, 3),
                new Item("CANDLE-2", "Candle", 500, 2),
                new Item("SCARF-3", "Scarf", 650, 1));
        assertEquals(OptionalLong.of(1150), BudgetBundle.bestPairTotal(items, 1200));
    }
}
