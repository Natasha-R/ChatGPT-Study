package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Component.Connection;
import thkoeln.st.st2praktikum.exercise.Component.Field;
import thkoeln.st.st2praktikum.exercise.Component.IComponentWithUUID;
import thkoeln.st.st2praktikum.exercise.Component.MiningMachine;

import java.util.UUID;

public class MiningMachineService {
    private UUIDManager uuidManager;
    private ConnectionManager connectionManager;

    public MiningMachineService() {
        uuidManager = new UUIDManager();
        connectionManager = new ConnectionManager();
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width, height);
        uuidManager.addGameComponent(field);
        return field.getUUID();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field field = (Field) uuidManager.getObjectFromUUID(fieldId);
        field.addWall(wall);
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
        UUID connectionUUID = connectionManager.createConnection(
                (Field) uuidManager.getObjectFromUUID(sourceFieldId),
                sourcePoint,
                (Field) uuidManager.getObjectFromUUID(destinationFieldId),
                destinationPoint, uuidManager);
        return connectionUUID;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(name);
        uuidManager.addGameComponent(machine);
        return machine.getUUID();
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        MiningMachine miningMachine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
        boolean result = false;
        switch (order.getOrderType()){
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                result = miningMachine.move(order.getOrderType(), order.getNumberOfSteps());
                break;
            case TRANSPORT:
                Connection connectionToUse = connectionManager.getConnection(miningMachine.getField().getUUID(), order.getGridId(), miningMachine.getPosition());
                if (connectionToUse == null) result = false;
                else result = connectionManager.useConnection(connectionToUse.getUUID());
                break;
            case ENTER:
                result = setMiningMachineOnField(miningMachineId, order.getGridId());
                break;
            default:

        }
        return result;
    }

    private boolean setMiningMachineOnField(UUID miningMachineId, UUID fieldID) {
        IComponentWithUUID findedField = uuidManager.getObjectFromUUID(fieldID);
        if (findedField == null) { return false; }
        Field field = (Field) findedField;
        if (field.addMachine((MiningMachine) uuidManager.getObjectFromUUID(miningMachineId))) {
            MiningMachine machine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
            machine.setField(field);
            machine.setPosition(new Point(0, 0));
        } else { return false; }
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        MiningMachine machine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
        return machine.getField().getUUID();
    }

    /**
     * This method returns the points a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the points of the mining machine
     */
    public Point getPoint(UUID miningMachineId){
        MiningMachine machine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
        return machine.getPosition();
    }
}
