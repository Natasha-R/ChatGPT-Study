package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Exception.SpawnMiningMachineException;
import thkoeln.st.st2praktikum.exercise.Exception.TeleportMiningMachineException;
import thkoeln.st.st2praktikum.exercise.Objects.*;

import javax.management.InvalidAttributeValueException;
import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {

    HashMap<UUID, Field> FieldList = new HashMap<UUID, Field>();
    HashMap<UUID, MiningMachine> MiningMachineList = new HashMap<UUID, MiningMachine>();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        var field = new Field(height,width);
        FieldList.put(field.Uuid,field);
        return field.Uuid;
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID fieldId, String obstacleString) {
        try {

        var field = FieldList.get(fieldId);
        field.addObstacle(obstacleString);

        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
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
        try{

        var sourceField = FieldList.get(sourceFieldId);
        var destinationField = FieldList.get(destinationFieldId);

        var connection = new Connection(sourceField,destinationField, new Coordinates(sourceCoordinate), new Coordinates(destinationCoordinate));

        sourceField.setConnection(connection);

        return connection.Uuid;

        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        var miningMachine = new MiningMachine(name);
        MiningMachineList.put(miningMachine.Uuid,miningMachine);
        return miningMachine.Uuid;
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        try {

            if(commandString.toCharArray()[0] != '[' || commandString.toCharArray()[commandString.length()-1] != ']')
                throw new InvalidAttributeValueException();

            var rawString = commandString.substring(1,commandString.length()-1);

            var miningMachine = MiningMachineList.get(miningMachineId);

            var command = rawString.split(",")[0];
            var information = rawString.split(",")[1];

            switch(command) {
                case "no":
                    miningMachine.moveNorth(Integer.parseInt(information));
                    break;
                case "ea":
                    miningMachine.moveEast(Integer.parseInt(information));
                    break;
                case "so":
                    miningMachine.moveSouth(Integer.parseInt(information));
                    break;
                case "we":
                    miningMachine.moveWest(Integer.parseInt(information));
                    break;

                    //teleport miningMachine
                case "tr":
                    miningMachine.teleport(miningMachine.getCurrentTile().connection);
                    break;

                    //spawns miningMachine
                case "en":
                    var field = FieldList.get(UUID.fromString(information));

                    field.spawnMiningMachine(miningMachine,new Coordinates(0,0));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + command);
            }


        } catch (InvalidAttributeValueException | SpawnMiningMachineException | TeleportMiningMachineException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return MiningMachineList.get(miningMachineId).CurrentField.Uuid;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        return MiningMachineList.get(miningMachineId).Position.toString();
    }
}
