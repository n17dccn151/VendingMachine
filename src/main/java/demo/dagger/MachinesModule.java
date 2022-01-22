package demo.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TanVOD on Jan, 2022
 */
@Module
public class MachinesModule {

    @Provides
    public Inventory<Coin> provideCashInventory() {
        return new Inventory<Coin>();
    }

    @Provides
    public Inventory<Item> provideItemInventory() {
        return new Inventory<Item>();
    }

    @Provides
    public Promotion providePromotion( ) {
        return new Promotion();
    }

}
