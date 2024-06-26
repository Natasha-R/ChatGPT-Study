package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {

    private HashMap<UUID,Field> fields = new HashMap<>();
    private HashMap<UUID, Movable> miningMachines = new HashMap<>();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height,width);
        fields.put(field.getFieldId(), field);
        return field.getFieldId();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field targetField = fields.get(fieldId);
        if(targetField == null) throw new NullPointerException();
        targetField.getWalls().add(wall);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Connection connection = new Connection(sourceFieldId,sourceCoordinate,destinationFieldId,destinationCoordinate);
        boolean sourceValid = fields.get(sourceFieldId).coordinateIsNotOutOfBounds(sourceCoordinate);
        boolean destinationValid = fields.get(sourceFieldId).coordinateIsNotOutOfBounds(destinationCoordinate);
        Field sourceField = fields.get(sourceFieldId);
        if(sourceField == null) throw new NullPointerException();
        if(sourceValid && destinationValid) {
            sourceField.addConnection(connection);
            return connection.getConnectionID();
        }
        throw new IllegalArgumentException();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachines.put(miningMachine.getId(),miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        Movable miningMachine = miningMachines.get(miningMachineId);
        if(miningMachine == null)throw new NullPointerException();
        return miningMachine.executeCommand(task,fields,miningMachines);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        UUID miningMachineFieldId = miningMachines.get(miningMachineId).getFieldId();
        if(miningMachineFieldId == null) throw new NullPointerException();
        return miningMachineFieldId;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinates of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId){
        Movable miningMachine = miningMachines.get(miningMachineId);
        if(miningMachine == null) throw new NullPointerException();
        return miningMachine.getCoordinate();
    }
}
