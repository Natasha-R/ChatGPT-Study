package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Connection;

import java.util.UUID;

@EnableJpaRepositories
public interface ConnectionRepository extends CrudRepository<Connection, UUID>
{
}
