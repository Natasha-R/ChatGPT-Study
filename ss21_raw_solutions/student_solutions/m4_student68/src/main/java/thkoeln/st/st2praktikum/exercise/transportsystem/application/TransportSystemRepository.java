package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;


import java.util.UUID;

@EnableJpaRepositories
public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {

}
