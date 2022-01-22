package demo.dagger;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MachinesModule_ProvideCashInventoryFactory implements Factory<Inventory<Coin>> {
  private final MachinesModule module;

  public MachinesModule_ProvideCashInventoryFactory(MachinesModule module) {
    this.module = module;
  }

  @Override
  public Inventory<Coin> get() {
    return provideInstance(module);
  }

  public static Inventory<Coin> provideInstance(MachinesModule module) {
    return proxyProvideCashInventory(module);
  }

  public static MachinesModule_ProvideCashInventoryFactory create(MachinesModule module) {
    return new MachinesModule_ProvideCashInventoryFactory(module);
  }

  public static Inventory<Coin> proxyProvideCashInventory(MachinesModule instance) {
    return Preconditions.checkNotNull(
        instance.provideCashInventory(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
