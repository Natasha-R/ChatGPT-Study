package thkoeln.st.st2praktikum.exercise.transportcategory.repo;


import org.springframework.data.jpa.repository.Query;
import thkoeln.st.st2praktikum.exercise.repo.NoBeanRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConnectionRepositry extends NoBeanRepository<Connection> {
    @Query("select con from Connection con  where con.sourceSpaceId = ?1 and con.destinationSpaceId = ?2 ")
    public List<Connection> findBySourceAndDestination(UUID sourceSpaceId, UUID destinationSpaceId);


}
