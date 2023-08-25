package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface IConnection extends IComponent {
    MiningMachine getMachineOnSourceField();
    MiningMachine getMachineOnDestinationField();
    Boolean verifyConnection(UUID searchedSourceFieldUUID, UUID searchedDestinationFieldUUID, Point searchedSourceFieldPosition);
}
