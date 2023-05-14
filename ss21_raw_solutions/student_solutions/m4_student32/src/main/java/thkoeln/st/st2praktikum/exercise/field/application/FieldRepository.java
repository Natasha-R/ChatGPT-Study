package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.UUID;

public interface FieldRepository extends CrudRepository<Field, UUID> {
}
