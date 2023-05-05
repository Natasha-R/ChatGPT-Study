package thkoeln.st.st2praktikum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.ShipDeck;
import thkoeln.st.st2praktikum.exercise.TransportTechnology;

import java.util.UUID;

@Repository
public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
    public TransportTechnology getTransportTechnologyByid(UUID id);
}
