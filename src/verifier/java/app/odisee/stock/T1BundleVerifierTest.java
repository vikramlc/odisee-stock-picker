package app.odisee.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Hidden verifier for Task 1 (fail-to-pass). Do not read before solving. */
class T1BundleVerifierTest {

    private static Item item(String sku, long price) {
        return new Item(sku, sku, price, 1);
    }

    @Test
    void reportedCase() {
        List<Item> items = List.of(item("CARD-1", 150), item("CANDLE-2", 500), item("SCARF-3", 650));
        assertEquals(OptionalLong.of(1150), BudgetBundle.bestPairTotal(items, 1200));
    }

    @Test
    void twoItemsOnly() {
        assertEquals(OptionalLong.of(30), BudgetBundle.bestPairTotal(List.of(item("A", 10), item("B", 20)), 30));
    }

    @Test
    void bestPairInTheMiddle() {
        List<Item> items = List.of(item("A", 50), item("B", 200), item("C", 300), item("D", 700), item("E", 900));
        assertEquals(OptionalLong.of(1000), BudgetBundle.bestPairTotal(items, 1050));
    }

    @Test
    void matchesBruteForceOnRandomInputs() {
        Random rng = new Random(7);
        int mismatches = 0;
        for (int round = 0; round < 500; round++) {
            int n = 2 + rng.nextInt(9);
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                items.add(item("S" + i, rng.nextInt(1000)));
            }
            long budget = rng.nextInt(2000);
            long best = -1;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    long sum = items.get(i).priceCents() + items.get(j).priceCents();
                    if (sum <= budget) {
                        best = Math.max(best, sum);
                    }
                }
            }
            OptionalLong want = best < 0 ? OptionalLong.empty() : OptionalLong.of(best);
            if (!want.equals(BudgetBundle.bestPairTotal(items, budget))) {
                mismatches++;
            }
        }
        assertEquals(0, mismatches);
    }
}
