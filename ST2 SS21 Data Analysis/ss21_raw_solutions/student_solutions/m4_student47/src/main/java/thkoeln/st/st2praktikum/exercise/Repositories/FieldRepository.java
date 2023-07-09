package thkoeln.st.st2praktikum.exercise.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.UUID;

@Repository
public interface FieldRepository extends CrudRepository<Field, UUID> {
}