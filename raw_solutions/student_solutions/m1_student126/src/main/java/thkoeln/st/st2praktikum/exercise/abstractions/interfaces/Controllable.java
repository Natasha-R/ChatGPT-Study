package thkoeln.st.st2praktikum.exercise.abstractions.interfaces;

import thkoeln.st.st2praktikum.exercise.entities.Field;
import thkoeln.st.st2praktikum.exercise.entities.FieldConnection;
import thkoeln.st.st2praktikum.exercise.entities.MiningMachine;
import thkoeln.st.st2praktikum.exercise.valueObjects.Barrier;
import thkoeln.st.st2praktikum.exercise.valueObjects.Coordinate;

import java.util.UUID;

public interface Controllable {

    boolean executeCommand(UUID machineId, String commandString);

    void addMiningMachine(MiningMachine miningMachine);

    void addField(Field field);

    void addBarrier(UUID fieldId,Barrier barrier);

    void addFieldConnection(FieldConnection connection);

    Coordinate getCoordinates(UUID miningMachine);
}
