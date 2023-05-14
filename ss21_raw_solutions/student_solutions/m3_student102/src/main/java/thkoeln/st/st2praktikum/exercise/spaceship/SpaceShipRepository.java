package thkoeln.st.st2praktikum.exercise.spaceship;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpaceShipRepository extends CrudRepository<SpaceShip , UUID> {
}
