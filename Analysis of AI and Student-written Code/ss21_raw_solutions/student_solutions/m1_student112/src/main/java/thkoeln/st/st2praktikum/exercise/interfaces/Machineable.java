package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

import java.util.UUID;

public interface Machineable {
    void setIsAtFieldId(UUID isAtFieldId);

    void setName(String name);

    void setCoordinate(CoordinatePair coordinate);

    UUID getIsAtFieldId();

    UUID getMiningmachineId();

    String getName();

    CoordinatePair getCoordinate();
}
