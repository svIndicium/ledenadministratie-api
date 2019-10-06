package hu.indicium.dev.ledenadministratie.util;

public interface Mapper<E, D> {
    D toDTO(E entity);

    E toEntity(D dto);
}
