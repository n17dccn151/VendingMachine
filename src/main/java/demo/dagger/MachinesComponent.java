package demo.dagger;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by TanVOD on Jan, 2022
 */
@Singleton
@Component(modules = MachinesModule.class)
public interface MachinesComponent {
    public SimpleMachine buildMachine();
}
