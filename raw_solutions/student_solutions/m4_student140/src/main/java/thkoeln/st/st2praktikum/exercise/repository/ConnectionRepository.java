package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Connection;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository extends CrudRepository<Connection, UUID> {

    @Override
    List<Connection> findAll();
}
