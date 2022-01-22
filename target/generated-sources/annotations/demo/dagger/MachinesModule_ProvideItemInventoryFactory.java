package demo.dagger;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MachinesModule_ProvideItemInventoryFactory implements Factory<Inventory<Item>> {
  private final MachinesModule module;

  public MachinesModule_ProvideItemInventoryFactory(MachinesModule module) {
    this.module = module;
  }

  @Override
  public Inventory<Item> get() {
    return provideInstance(module);
  }

  public static Inventory<Item> provideInstance(MachinesModule module) {
    return proxyProvideItemInventory(module);
  }

  public static MachinesModule_ProvideItemInventoryFactory create(MachinesModule module) {
    return new MachinesModule_ProvideItemInventoryFactory(module);
  }

  public static Inventory<Item> proxyProvideItemInventory(MachinesModule instance) {
    return Preconditions.checkNotNull(
        instance.provideItemInventory(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
