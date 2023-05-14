package thkoeln.st.st2praktikum.exercise.domainprimitives;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CommandRepository extends CrudRepository<Command, UUID> {
}
