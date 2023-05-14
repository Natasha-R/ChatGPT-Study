package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {
    private HashMap<UUID, Field> groupOfFields = new HashMap<>();
    private HashMap<UUID, MiningMachine> groupOfMiningMachines = new HashMap<>();
    private HashMap<UUID, Connection> groupOfConnections = new HashMap<>();
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID newFieldUUID = UUID.randomUUID();
        groupOfFields.put(newFieldUUID, new Field(newFieldUUID, height, width));
        return newFieldUUID;
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        groupOfFields.get(fieldId).addBarrier(barrier);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Vector2D sourceVector2D, UUID destinationFieldId, Vector2D destinationVector2D) {
        UUID connectionNewUUID = UUID.randomUUID();
        Connection newConnection = new Connection(connectionNewUUID, sourceFieldId, destinationFieldId, sourceVector2D, destinationVector2D);

        if(groupOfFields.get(sourceFieldId).addConnection(newConnection, groupOfFields.get(destinationFieldId)))
            groupOfConnections.put(connectionNewUUID, newConnection);
        else
            throw new RuntimeException("Connection is outside of a map");

        return  connectionNewUUID;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID miningMachineNewUUID = UUID.randomUUID();
        groupOfMiningMachines.put(miningMachineNewUUID, new MiningMachine(miningMachineNewUUID ,name));
        return miningMachineNewUUID;
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        MiningMachine miningMachine = groupOfMiningMachines.get(miningMachineId);
        Map desiredMap = groupOfFields.get(order.getGridId());

        if(order.getOrderType() == OrderType.ENTER){
            if(desiredMap.addBlockingObject(miningMachine, new Vector2D(0,0))){
                miningMachine.placeOnMap(desiredMap ,new Vector2D(0,0));
                return true;
            }
            return false;
        }
        else if(order.getOrderType() == OrderType.TRANSPORT){
            return miningMachine.useConnection(desiredMap);
        }
        else{
            return miningMachine.move(order);
        }
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return groupOfMiningMachines.get(miningMachineId).getMap().getUUID();
    }

    /**
     * This method returns the vector2Ds a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the vector2Ds of the mining machine
     */
    public Vector2D getVector2D(UUID miningMachineId){
        return groupOfMiningMachines.get(miningMachineId).getLocation();
    }
}
