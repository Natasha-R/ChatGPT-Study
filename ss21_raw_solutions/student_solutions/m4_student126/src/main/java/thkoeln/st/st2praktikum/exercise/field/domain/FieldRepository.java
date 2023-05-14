package thkoeln.st.st2praktikum.exercise.field.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FieldRepository extends CrudRepository<Field, UUID> {
}
