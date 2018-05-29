package by.bytechs.service;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */

public interface Service<E, D> {
    D entityToDTO(E entity);
    E dtoToEntity(D dto);
}
