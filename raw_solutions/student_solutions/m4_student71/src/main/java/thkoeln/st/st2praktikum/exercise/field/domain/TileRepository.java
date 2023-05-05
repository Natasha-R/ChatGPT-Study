package thkoeln.st.st2praktikum.exercise.field.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported = false)
public interface TileRepository extends CrudRepository<Tile, Long> {
}