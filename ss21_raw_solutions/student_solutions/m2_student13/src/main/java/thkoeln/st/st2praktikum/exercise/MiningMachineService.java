package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {

    //HashMap for the assignment of the object to its id
    private HashMap<UUID,Field> uuidFieldHashMap = new HashMap<UUID,Field>();
    private HashMap<UUID,MiningMachine> uuidMiningMachineHashMap = new HashMap<UUID,MiningMachine>();
    private HashMap<UUID,Connection> uuidConnectionHashMap = new HashMap<UUID,Connection>();
    private World world = new World();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {

        Field field = new Field(height,width);
        uuidFieldHashMap.put(field.getUuid(),field);
        return field.getUuid();

        //throw new UnsupportedOperationException();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field field = uuidFieldHashMap.get(fieldId);
        field.placeWall(wall);


        //throw new UnsupportedOperationException();
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {

        Field sourceField = uuidFieldHashMap.get(sourceFieldId);
        Field destinationField = uuidFieldHashMap.get(destinationFieldId);

        Connection connection = world.createConnection(sourceField, sourcePoint, destinationField, destinationPoint);
        uuidConnectionHashMap.put(connection.getUuid(),connection);

        return connection.getUuid();

        //throw new UnsupportedOperationException();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        uuidMiningMachineHashMap.put(miningMachine.getUuid(),miningMachine);
        return miningMachine.getUuid();
        //throw new UnsupportedOperationException();
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {

        MiningMachine miningMachine = uuidMiningMachineHashMap.get(miningMachineId);
        return miningMachine.execute(task, uuidFieldHashMap, uuidConnectionHashMap);

        //throw new UnsupportedOperationException();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        MiningMachine miningMachine = uuidMiningMachineHashMap.get(miningMachineId);
        return miningMachine.getRoom().getField().getUuid();

        //throw new UnsupportedOperationException();
    }

    /**
     * This method returns the points a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the points of the mining machine
     */
    public Point getPoint(UUID miningMachineId){
        MiningMachine miningMachine = uuidMiningMachineHashMap.get(miningMachineId);
        return miningMachine.currentPosition();
        //throw new UnsupportedOperationException();
    }
}
