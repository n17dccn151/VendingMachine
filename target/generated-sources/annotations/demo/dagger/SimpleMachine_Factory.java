package demo.dagger;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SimpleMachine_Factory implements Factory<SimpleMachine> {
  private final Provider<Inventory<Coin>> cashInventoryProvider;

  private final Provider<Inventory<Item>> itemInventoryProvider;

  private final Provider<Promotion> promotionProvider;

  public SimpleMachine_Factory(
      Provider<Inventory<Coin>> cashInventoryProvider,
      Provider<Inventory<Item>> itemInventoryProvider,
      Provider<Promotion> promotionProvider) {
    this.cashInventoryProvider = cashInventoryProvider;
    this.itemInventoryProvider = itemInventoryProvider;
    this.promotionProvider = promotionProvider;
  }

  @Override
  public SimpleMachine get() {
    return provideInstance(cashInventoryProvider, itemInventoryProvider, promotionProvider);
  }

  public static SimpleMachine provideInstance(
      Provider<Inventory<Coin>> cashInventoryProvider,
      Provider<Inventory<Item>> itemInventoryProvider,
      Provider<Promotion> promotionProvider) {
    return new SimpleMachine(
        cashInventoryProvider.get(), itemInventoryProvider.get(), promotionProvider.get());
  }

  public static SimpleMachine_Factory create(
      Provider<Inventory<Coin>> cashInventoryProvider,
      Provider<Inventory<Item>> itemInventoryProvider,
      Provider<Promotion> promotionProvider) {
    return new SimpleMachine_Factory(
        cashInventoryProvider, itemInventoryProvider, promotionProvider);
  }

  public static SimpleMachine newSimpleMachine(
      Inventory<Coin> cashInventory, Inventory<Item> itemInventory, Promotion promotion) {
    return new SimpleMachine(cashInventory, itemInventory, promotion);
  }
}
