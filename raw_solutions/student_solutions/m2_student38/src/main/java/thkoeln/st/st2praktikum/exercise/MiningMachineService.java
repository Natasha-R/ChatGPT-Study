package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {

    private HashMap<UUID,Field> fields;
    private HashMap<UUID,MiningMachine> miningMachines;

    public HashMap<UUID, Field> getFields() {
        return fields;
    }

    public HashMap<UUID, MiningMachine> getMiningMachines() {
        return miningMachines;
    }

    public HashMap<UUID, Connection> getConnections() {
        return connections;
    }

    private HashMap<UUID,Connection> connections;

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        if (height < 0 || width < 0)
            throw new IllegalArgumentException("One or more parameters are negative!");

        if (fields == null)
            fields = new HashMap<>();

        UUID fieldID = UUID.randomUUID();
        fields.put(fieldID, new Field(height,width,fieldID));
        return fieldID;
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        if (fieldId == null || fields.get(fieldId) == null || obstacle == null)
            throw new IllegalArgumentException();

        Field field = fields.get(fieldId);
        field.addObstacles(obstacle);
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
        if (sourceFieldId == null || sourcePoint == null || destinationFieldId == null || destinationPoint == null)
            throw new IllegalArgumentException("Null values are not allowed");

        if (fields.get(sourceFieldId).getHeight() <= sourcePoint.getY() || fields.get(sourceFieldId).getWidth() <= sourcePoint.getX() ||
                fields.get(destinationFieldId).getHeight() <= destinationPoint.getY() || fields.get(destinationFieldId).getWidth() <= destinationPoint.getX())
            throw new IllegalArgumentException("Connection has to be in bounds!");

        if (connections == null)
            connections = new HashMap<>();

        UUID connectionID = UUID.randomUUID();
        connections.put(connectionID,new Connection(fields.get(sourceFieldId),fields.get(destinationFieldId),sourcePoint,destinationPoint));
        return connectionID;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        if (miningMachines == null)
            miningMachines = new HashMap<>();

        UUID miningMachineID = UUID.randomUUID();
        miningMachines.put(miningMachineID,new MiningMachine(name));
        return miningMachineID;
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        if (miningMachineId == null || command == null)
            throw new IllegalArgumentException();
        return miningMachines.get(miningMachineId).executeCommand(command,fields,connections);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        if (miningMachineId == null)
            throw new IllegalArgumentException();
        return miningMachines.get(miningMachineId).getFieldID();
    }

    /**
     * This method returns the points a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the points of the mining machine
     */
    public Point getPoint(UUID miningMachineId){
        if (miningMachineId == null)
            throw new IllegalArgumentException();
        return miningMachines.get(miningMachineId).getLocation();
    }
}
