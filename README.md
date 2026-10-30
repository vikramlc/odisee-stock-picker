# odisee-stock-picker

A small, original practice repository for **Odisee** AI-enabled algorithms
mocks. It is a slice of a shop's inventory module: an in-memory inventory
and a "gift bundle" picker. Java 21+, Gradle, JUnit 5. No other dependencies.

Licence: MIT (see `LICENSE`).

## Setup

Requires a JDK 21 or newer.

```bash
./gradlew test
```

On `main`, **one test fails on purpose**: `BudgetBundleBugReportTest` reproduces bug #7.

## Layout

| Path | What it is |
|---|---|
| `src/main/java/app/odisee/stock/Item.java` | Item record (sku, name, priceCents, quantity) |
| `src/main/java/app/odisee/stock/Inventory.java` | In-memory inventory keyed by SKU (`HashMap`) |
| `src/main/java/app/odisee/stock/BudgetBundle.java` | `bestPairTotal(items, budgetCents)`: best two-item bundle within a budget (sort + two pointers) |
| `src/test/java/...` | Visible tests |

## Tasks

Each task is graded on its own, starting from `main`. Use any AI assistant
you like, but you own the result: read every edit, run the tests, and never
change a test's expected value or hard-code a test's input to make it pass.

### Task 1 — fix bug #7 (`t1-bundle-bug`)

`BudgetBundleBugReportTest` fails: with a 12.00 budget the picker offers an
8.00 bundle although 5.00 + 6.50 fits. Find the root cause in
`BudgetBundle.bestPairTotal` and fix it for **every** input, keeping
O(n log n) time. Add one more test of your own that fails before the fix.
Be ready to explain why the two-pointer walk is correct.

### Task 2 — the k cheapest items (`t2-cheapest-k`)

Add `List<Item> Inventory.cheapest(int k)`:

* Returns up to `k` **in-stock** items, cheapest first; equal prices are ordered by SKU.
* `k <= 0` returns an empty list; `k` larger than the number of in-stock items returns all of them.
* Runs in O(n log k) time and O(k) extra space (n = items in the inventory). A full sort is not enough.
* Be ready to state and defend the complexity.

Stage 3 (if time allows): with 1,000,000 items and k = 10, `cheapest` should
return in well under a second.

## For graders

* Golden solutions: branches `solution/t1-bundle-bug` and `solution/t2-cheapest-k`.
* Hidden verifier tests: branch `verifier`, directory `src/verifier/java/...`
  (`T1BundleVerifierTest`, `T2CheapestVerifierTest`). Copy the task's class into
  `src/test/java` to run it.
  * fail-to-pass: the verifier class for the task.
  * pass-to-pass: `InventoryTest` and `BudgetBundleTest` **as they are on `main`**.

```bash
git checkout <candidate>
git checkout main -- src/test/
git checkout verifier -- src/verifier/
cp src/verifier/java/app/odisee/stock/T1BundleVerifierTest.java src/test/java/app/odisee/stock/
./gradlew test --tests '*T1BundleVerifierTest'                       # fail-to-pass
./gradlew test --tests '*InventoryTest' --tests '*BudgetBundleTest'  # pass-to-pass
```

Please solve the tasks before looking at those branches.
