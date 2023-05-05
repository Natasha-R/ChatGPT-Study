package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.Connection;
import thkoeln.st.st2praktikum.exercise.Field;

import java.util.UUID;


@RepositoryRestResource(exported = false)
public interface FieldRepository extends CrudRepository<Field, UUID> {
}