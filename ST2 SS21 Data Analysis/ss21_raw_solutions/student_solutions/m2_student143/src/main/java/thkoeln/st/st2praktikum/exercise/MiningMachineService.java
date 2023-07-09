package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class MiningMachineService {

    private Map<UUID,Field> fields;
    private Map<UUID,Connection> connections;
    private Map<UUID,MiningMachine> machines;

    public MiningMachineService(){
        fields = new HashMap<>();
        connections = new HashMap<>();
        machines = new HashMap<>();
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height,width);
        fields.put(field.id,field);
        return field.id;
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        fields.get(fieldId).add(wall);
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
        Connection connection = new Connection(
                fields.get(sourceFieldId),
                fields.get(destinationFieldId),
                sourceCoordinate,
                destinationCoordinate
        );

        connections.put(connection.id,connection);
        return connection.id;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(name, new Coordinate(0,0));
        machines.put(machine.id,machine);
        return machine.id;
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        MiningMachine machine = machines.get(miningMachineId);
        switch (command.getCommandType()){
            case NORTH:
                return moveMachineMultipleSteps(machine,Direction.NORTH,command.getNumberOfSteps());
            case WEST:
                return moveMachineMultipleSteps(machine,Direction.WEST,command.getNumberOfSteps());
            case SOUTH:
                return moveMachineMultipleSteps(machine,Direction.SOUTH,command.getNumberOfSteps());
            case EAST:
                return moveMachineMultipleSteps(machine,Direction.EAST,command.getNumberOfSteps());
            case TRANSPORT:
                return transportMachineToField(machine, fields.get(command.getGridId()));
            case ENTER:
                return spawnMachineToField(machine,fields.get(command.getGridId()));
        }

        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for(Map.Entry<UUID,Field> fieldEntry : fields.entrySet()){
            for(FieldItem item : fieldEntry.getValue().getItems()){
                if(item instanceof MiningMachine){
                    MiningMachine machine = (MiningMachine) item;
                    if(miningMachineId == machine.id){
                        return fieldEntry.getValue().id;
                    }
                }
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinates of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId){
        return machines.get(miningMachineId).getCoordinate();
    }



    private boolean spawnMachineToField(MiningMachine machine, Field field){
        Coordinate zeroCoordinate = new Coordinate(0,0);
        for(FieldItem item : field.getItems()){
            Coordinate coordinate = item.getCoordinate();
            if(item instanceof MiningMachine && coordinate.equals(zeroCoordinate)){
                return false;
            }
        }
        machine.setCoordinate(zeroCoordinate);
        field.add(machine);
        return true;
    }

    private boolean transportMachineToField(MiningMachine machine, Field destinationField){
        for(Map.Entry<UUID,Connection> connectionEntry : connections.entrySet()){
            Connection connection = connectionEntry.getValue();
            if(connection.getDestinationField() == destinationField && connection.teleport(machine)){
                return true;
            }
        }
        return false;
    }

    private boolean moveMachineMultipleSteps(MiningMachine machine, Direction direction, int steps){
        if(direction == Direction.NONE) { return false; }

        Field field = fields.get(getMiningMachineFieldId(machine.id));
        while(steps > 0) {
            boolean machineIsColliding = false;
            for (FieldItem item : field.getItems()) {
                if (item instanceof Collidable
                        && item != machine
                        && machine.isCollidingWith((Collidable) item, direction)) {
                    machineIsColliding = true;
                    break;
                }
            }
            if (!machineIsColliding) {
                machine.setCoordinate(machine.nextCoordinate(direction));
            }
            steps--;
        }
        return true;
    }

}
