package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Searchable {
    UUID getMiningMachineFieldId(UUID miningMachineId);
    Point getMiningMachinePoint(UUID miningMachineId);
}

