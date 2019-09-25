package hu.indicium.dev.ledenadministratie.hooks;

public interface Hook<T> {
    void execute(T oldEntity, T newEntity);
}
