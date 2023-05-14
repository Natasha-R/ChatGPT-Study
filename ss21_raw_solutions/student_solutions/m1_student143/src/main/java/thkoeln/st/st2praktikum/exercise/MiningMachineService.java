package thkoeln.st.st2praktikum.exercise;


import java.util.*;

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
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID fieldId, String wallString) {
        Field field = fields.get(fieldId);
        Wall wall = Wall.fromString(wallString);
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
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {

        Field sourceField = fields.get(sourceFieldId);
        Field destinationField = fields.get(destinationFieldId);

        Connection connection = new Connection(
                sourceField,
                destinationField,
                Coordinate.fromString(sourceCoordinate),
                Coordinate.fromString(destinationCoordinate)
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
        MiningMachine machine = new MiningMachine(name,new Coordinate(0,0));
        machines.put(machine.id,machine);
        return machine.id;
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        MiningMachine machine = machines.get(miningMachineId);
        String [] actionStrings = commandString
                .replaceAll("\\[|\\]","")
                .split(",");
        String action = actionStrings[0];
        String actionObject = actionStrings[1];

        switch (action){
            case "en": return spawnMachineToField(machine,fields.get(UUID.fromString(actionObject)));
            case "tr": return transportMachineToField(machine, fields.get(UUID.fromString(actionObject)));
            case "we": return moveMachineMultipleSteps(machine,Direction.WEST,Integer.parseInt(actionObject));
            case "so": return moveMachineMultipleSteps(machine,Direction.SOUTH,Integer.parseInt(actionObject));
            case "no": return moveMachineMultipleSteps(machine,Direction.NORTH,Integer.parseInt(actionObject));
            case "ea": return moveMachineMultipleSteps(machine,Direction.EAST,Integer.parseInt(actionObject));
        }
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for(Map.Entry<UUID,Field> fieldEntry : fields.entrySet()){
            for(Placable placable : fieldEntry.getValue().getItems()){
                if(placable instanceof MiningMachine){
                    MiningMachine machine = (MiningMachine) placable;
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
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        return machines.get(miningMachineId).toString();
    }



    private boolean spawnMachineToField(MiningMachine machine, Field field){
        Coordinate zeroCoordinate = new Coordinate(0,0);
        for(Placable placable : field.getItems()){
            Coordinate coordinate = placable.getCoordinate();
            if(placable instanceof MiningMachine && coordinate.equals(zeroCoordinate)){
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
            for (Placable placable : field.getItems()) {
                if (placable instanceof Collidable
                        && placable != machine
                        && machine.isCollidingWith((Collidable) placable, direction)) {
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
