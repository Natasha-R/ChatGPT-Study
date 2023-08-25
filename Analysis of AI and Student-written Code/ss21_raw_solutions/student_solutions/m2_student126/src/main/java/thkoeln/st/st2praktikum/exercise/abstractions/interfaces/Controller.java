package thkoeln.st.st2praktikum.exercise.abstractions.interfaces;

import thkoeln.st.st2praktikum.exercise.*;

import java.util.UUID;

public interface Controller {

    boolean executeCommand(UUID machineId, Command command);

    void addMiningMachine(MiningMachine miningMachine);

    void addField(Field field);

    void addBarrier(UUID fieldId,Barrier barrier);

    void addFieldConnection(FieldConnection connection);

    Coordinate getCoordinates(UUID miningMachine);
}
