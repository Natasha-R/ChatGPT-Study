package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;


import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpaceshipRepository extends CrudRepository<Spaceship , UUID> {
}


