package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TransportSystem;

import java.util.UUID;

@EnableJpaRepositories
public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {

}
