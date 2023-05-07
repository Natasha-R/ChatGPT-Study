package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
}