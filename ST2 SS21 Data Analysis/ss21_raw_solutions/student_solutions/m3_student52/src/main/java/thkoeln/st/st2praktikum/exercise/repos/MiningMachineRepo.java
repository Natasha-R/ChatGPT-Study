package thkoeln.st.st2praktikum.exercise.repos;

import org.springframework.data.jpa.repository.Query;
import thkoeln.st.st2praktikum.exercise.MiningMachine;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.List;
import java.util.UUID;

public interface MiningMachineRepo extends AbstractRepo<MiningMachine> {
    @Query("select mm from MiningMachine mm where mm.fieldId = ?1 and mm.point = ?2")
    public List<MiningMachine> findByCoords(UUID fieldId, Point point);
}
