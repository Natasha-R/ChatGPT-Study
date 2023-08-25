package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.field.Connection;
import thkoeln.st.st2praktikum.exercise.field.Field;
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
     * @param fieldId  the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        DataStorage.getFieldMap().get(fieldId).ceckPointToBeWithinBoarders(obstacle.getStart());
        DataStorage.getFieldMap().get(fieldId).ceckPointToBeWithinBoarders(obstacle.getEnd());
        DataStorage.getFieldMap().get(fieldId).getObstacles().add(obstacle);
    }


    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     *
     * @param sourceFieldId      the ID of the field where the entry point of the connection is located
     * @param sourcePoint        the points of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint   the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        Connection newConnection = new Connection(sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);
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
     * This method lets the mining machine execute a task.
     *
     * @param miningMachineId the ID of the mining machine
     * @param task            the given task
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on squares with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a obstacle or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        Moveable miningMachine = DataStorage.getMoveableMap().get(miningMachineId);
        return miningMachine.move(task);
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
     * This method returns the points a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the points of the mining machine
     */
    public Point getPoint(UUID miningMachineId) {
        return DataStorage.getMoveableMap().get(miningMachineId).getPosition();
    }

}
