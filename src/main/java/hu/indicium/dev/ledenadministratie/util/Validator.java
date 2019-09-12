package hu.indicium.dev.ledenadministratie.util;

public interface Validator<T> {
    void validate(T entity);
}
