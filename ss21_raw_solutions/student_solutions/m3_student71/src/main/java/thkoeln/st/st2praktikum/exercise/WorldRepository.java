package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.Tile;
import thkoeln.st.st2praktikum.exercise.World;


@RepositoryRestResource(exported = false)
public interface WorldRepository extends CrudRepository<World, Long> {
}