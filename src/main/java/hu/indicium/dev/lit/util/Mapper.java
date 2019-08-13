package hu.indicium.dev.lit.util;

public interface Mapper<E, D> {
    E toEntity(D dto);

    D toDTO(E entity);
}
