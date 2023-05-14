package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.data.jpa.repository.Query;
import thkoeln.st.st2praktikum.exercise.core.AbstractRepo;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.List;
import java.util.UUID;

public interface MiningMachineRepo extends AbstractRepo<MiningMachine> {
    @Query("select mm from MiningMachine mm where mm.fieldId = ?1 and mm.point = ?2")
    public List<MiningMachine> findByCoords(UUID fieldId, Point point);
}
