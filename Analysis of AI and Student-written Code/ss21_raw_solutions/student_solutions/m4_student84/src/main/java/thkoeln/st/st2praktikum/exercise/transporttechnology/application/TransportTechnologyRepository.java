package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
}