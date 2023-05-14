package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface SearchInterface {
    UUID getMiningMachineFieldId(UUID miningMachineId);
    Point getPoint(UUID miningMachineId);
}

