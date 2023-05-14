package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Spaceship;

import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository< TranportCategory , UUID> {

}

