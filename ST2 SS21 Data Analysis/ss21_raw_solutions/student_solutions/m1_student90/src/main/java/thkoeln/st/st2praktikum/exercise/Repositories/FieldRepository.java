package thkoeln.st.st2praktikum.exercise.Repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Entities.Field;

import java.util.Optional;
import java.util.UUID;

public interface FieldRepository extends CrudRepository<Field, UUID> {

}
