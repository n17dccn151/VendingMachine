package demo.dagger;

import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerMachinesComponent implements MachinesComponent {
  private MachinesModule machinesModule;

  private DaggerMachinesComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static MachinesComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.machinesModule = builder.machinesModule;
  }

  @Override
  public SimpleMachine buildMachine() {
    return new SimpleMachine(
        MachinesModule_ProvideCashInventoryFactory.proxyProvideCashInventory(machinesModule),
        MachinesModule_ProvideItemInventoryFactory.proxyProvideItemInventory(machinesModule),
        MachinesModule_ProvidePromotionFactory.proxyProvidePromotion(machinesModule));
  }

  public static final class Builder {
    private MachinesModule machinesModule;

    private Builder() {}

    public MachinesComponent build() {
      if (machinesModule == null) {
        this.machinesModule = new MachinesModule();
      }
      return new DaggerMachinesComponent(this);
    }

    public Builder machinesModule(MachinesModule machinesModule) {
      this.machinesModule = Preconditions.checkNotNull(machinesModule);
      return this;
    }
  }
}
