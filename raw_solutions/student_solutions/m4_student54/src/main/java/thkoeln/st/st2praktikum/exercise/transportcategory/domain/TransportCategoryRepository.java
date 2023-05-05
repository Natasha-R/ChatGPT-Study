package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}
