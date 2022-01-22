package demo.dagger;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MachinesModule_ProvidePromotionFactory implements Factory<Promotion> {
  private final MachinesModule module;

  public MachinesModule_ProvidePromotionFactory(MachinesModule module) {
    this.module = module;
  }

  @Override
  public Promotion get() {
    return provideInstance(module);
  }

  public static Promotion provideInstance(MachinesModule module) {
    return proxyProvidePromotion(module);
  }

  public static MachinesModule_ProvidePromotionFactory create(MachinesModule module) {
    return new MachinesModule_ProvidePromotionFactory(module);
  }

  public static Promotion proxyProvidePromotion(MachinesModule instance) {
    return Preconditions.checkNotNull(
        instance.providePromotion(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
