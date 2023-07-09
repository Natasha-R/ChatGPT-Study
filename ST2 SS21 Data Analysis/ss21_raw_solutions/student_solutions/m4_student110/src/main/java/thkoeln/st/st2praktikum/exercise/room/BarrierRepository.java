package thkoeln.st.st2praktikum.exercise.room;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;

import java.util.UUID;

public interface BarrierRepository extends CrudRepository<Barrier, UUID> {

}
