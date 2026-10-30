package app.odisee.stock;

import java.util.List;
import java.util.OptionalLong;

/** "Gift bundle" helper: pick two different items for a customer's budget. */
public final class BudgetBundle {

    private BudgetBundle() {}

    /**
     * The highest total price of two different items that does not exceed
     * {@code budgetCents}, or empty when no two items fit.
     *
     * <p>Sorts the prices, then walks two pointers inward: O(n log n) time, O(n) space.
     */
    public static OptionalLong bestPairTotal(List<Item> items, long budgetCents) {
        long[] prices = items.stream().mapToLong(Item::priceCents).sorted().toArray();
        long best = -1;
        int lo = 0;
        int hi = prices.length - 1;
        while (lo < hi) {
            long sum = prices[lo] + prices[hi];
            if (sum <= budgetCents) {
                best = Math.max(best, sum);
                lo++;
            } else {
                hi--;
            }
        }
        return best < 0 ? OptionalLong.empty() : OptionalLong.of(best);
    }
}
