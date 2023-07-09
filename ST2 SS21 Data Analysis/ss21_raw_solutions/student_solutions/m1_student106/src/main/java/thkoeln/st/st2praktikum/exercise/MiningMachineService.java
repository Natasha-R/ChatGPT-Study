package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.field.Connection;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.field.Obsticale;
import thkoeln.st.st2praktikum.exercise.movable.MiningMachine;
import thkoeln.st.st2praktikum.exercise.movable.Moveable;

import java.util.UUID;

public class MiningMachineService {

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field newField = new Field(height, width);
        DataStorage.addToFieldMap(newField.getUuid(), newField);
        return DataStorage.getFieldMap().get(newField.getUuid()).getUuid();
    }

    /**
     * This method adds a obstacle to a given field.
     *
     * @param fieldId        the ID of the field the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID fieldId, String obstacleString) {
        Obsticale newObsticale = new Obsticale(fieldId, obstacleString);
        DataStorage.addToObsticalMap(newObsticale.getUuid(), newObsticale);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one command.
     *
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        Connection newConnection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        DataStorage.addToConnectionMap(newConnection.getUuid(), newConnection);
        return DataStorage.getConnectionMap().get(newConnection.getUuid()).getUuid();
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        Moveable newMoveable = new MiningMachine(name);
        DataStorage.addToMoveableMap(newMoveable.getUuid(), newMoveable);
        return DataStorage.getMoveableMap().get(newMoveable.getUuid()).getUuid();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param commandString   the given command, encoded as a String:
     *                        "[command, steps]" for movement, e.g. "[we,2]"
     *                        "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another field
     *                        "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a obstacle or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        Moveable miningMachine = DataStorage.getMoveableMap().get(miningMachineId);
        return miningMachine.chooseAction(commandString);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return DataStorage.getMoveableMap().get(miningMachineId).getFieldId();
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId) {
        return DataStorage.getMoveableMap().get(miningMachineId).getPosition().getCoordinateString();
    }
}
