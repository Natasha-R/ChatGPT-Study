package thkoeln.st.st2praktikum.exercise.abstractions.interfaces;

import thkoeln.st.st2praktikum.exercise.*;

import java.util.UUID;

public interface Controller {

    boolean executeCommand(MiningMachine miningMachine, Field field, FieldConnection connection, Command command);

}
