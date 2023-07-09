package thkoeln.st.st2praktikum.exercise.field.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.SpawnMiningMachineException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TeleportMiningMachineException;

import javax.management.InvalidAttributeValueException;

public interface IContent {
    void executeCommand(Command command) throws InvalidAttributeValueException, TeleportMiningMachineException, SpawnMiningMachineException;
}
