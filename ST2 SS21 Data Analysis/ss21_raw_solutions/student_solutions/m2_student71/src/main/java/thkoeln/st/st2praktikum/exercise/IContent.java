package thkoeln.st.st2praktikum.exercise;

import javax.management.InvalidAttributeValueException;

public interface IContent {
    void executeCommand(Command command) throws InvalidAttributeValueException, TeleportMiningMachineException, SpawnMiningMachineException;
}
