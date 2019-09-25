package hu.indicium.dev.ledenadministratie.hooks;

import java.util.List;

public class HookGroup<T> implements Hook<T>, CreationHook<T>, UpdateHook<T> {

    private List<Hook<T>> hooks;

    public HookGroup(List<Hook<T>> hooks) {
        this.hooks = hooks;
    }

    @Override
    public void execute(T oldEntity, T newEntity) {
        for (Hook<T> hook : hooks) {
            hook.execute(oldEntity, newEntity);
        }
    }
}
