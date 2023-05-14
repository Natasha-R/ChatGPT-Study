package thkoeln.st.st2praktikum.exercise.field.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FieldRepository extends CrudRepository<Field, UUID> {
    Field getFieldByFieldId(UUID fieldId);
}
