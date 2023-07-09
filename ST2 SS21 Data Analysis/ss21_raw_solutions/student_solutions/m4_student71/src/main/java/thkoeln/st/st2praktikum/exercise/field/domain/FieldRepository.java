package thkoeln.st.st2praktikum.exercise.field.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;


@RepositoryRestResource(exported = false)
public interface FieldRepository extends CrudRepository<Field, UUID> {
}