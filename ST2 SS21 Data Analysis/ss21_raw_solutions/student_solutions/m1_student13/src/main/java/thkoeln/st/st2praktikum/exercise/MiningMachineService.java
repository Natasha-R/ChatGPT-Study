package thkoeln.st.st2praktikum.exercise;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;
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
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID fieldId, String wallString) {

        Field field = uuidFieldHashMap.get(fieldId);
        Wall wall = world.createWall(field,wallString);
        field.placeWall(wall);

        //throw new UnsupportedOperationException();
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {

        Field sourceField = uuidFieldHashMap.get(sourceFieldId);
        Field destinationField = uuidFieldHashMap.get(destinationFieldId);
        Connection connection;

        connection = world.createConnection(sourceField,destinationField,sourceCoordinate,destinationCoordinate);
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
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */

    public Boolean executeCommand(UUID miningMachineId, String commandString) {

        MiningMachine miningMachine = uuidMiningMachineHashMap.get(miningMachineId);
        return miningMachine.execute(commandString, uuidFieldHashMap, uuidConnectionHashMap);
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
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        MiningMachine miningMachine = uuidMiningMachineHashMap.get(miningMachineId);
        return miningMachine.currentPosition();



        //throw new UnsupportedOperationException();
    }


}
