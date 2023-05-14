package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {

    HashMap<UUID,Field> fields;
    HashMap<UUID,MiningMachine> miningMachines;
    HashMap<UUID,Connection> connections;

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        if (height < 0 || width < 0)
            throw new IllegalArgumentException();

        if (fields == null)
            fields = new HashMap<>();

        UUID fieldID = UUID.randomUUID();
        fields.put(fieldID, new Field(height,width,fieldID));
        return fieldID;
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID fieldId, String obstacleString) {
        if (fieldId == null || fields.get(fieldId) == null || obstacleString == null)
            throw new IllegalArgumentException();

        Field field = fields.get(fieldId);

        String[] locations = Arrays.toString(obstacleString.split("-")).
                replaceAll("\\s","").
                replaceAll("[\\[\\]]", "").
                replaceAll("[()]","").
                split(",");


        int startY = determineRealY(Integer.parseInt(locations[1]),field);
        int EndY = determineRealY(Integer.parseInt(locations[3]),field);

        for (int j = Integer.parseInt(locations[0]); j < Integer.parseInt(locations[2]); j++){
            field.map[startY][j].wSO = true;
            field.map[startY+1][j].wNO = true;
        }

        for (int i = startY; i < EndY; i++){
            field.map[i][Integer.parseInt(locations[0])].wWE = true;
            field.map[i][Integer.parseInt(locations[2])-1].wEA = true;
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
        if (sourceFieldId == null || sourceCoordinate == null || destinationFieldId == null || destinationCoordinate == null)
            throw new IllegalArgumentException();

        if (connections == null)
            connections = new HashMap<>();

        UUID connectionID = UUID.randomUUID();
        connections.put(connectionID,new Connection(sourceFieldId,destinationFieldId,sourceCoordinate,destinationCoordinate));
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
        if (miningMachineId == null || commandString == null)
            return false;

        String[] commands = commandString.replaceAll("[\\[\\]]", "").split(",");
        Field field;
        MiningMachine miningMachine = miningMachines.get(miningMachineId);

        switch (commands[0]){
            case "en":
                field = fields.get(UUID.fromString(commands[1]));
                if (field.map[0][field.map.length-1].busy)
                    return false;
                else{
                    miningMachine.fieldID = field.fieldID;
                    field.map[0][field.map.length-1].busy = true;
                    return true;
                }
            case "tr":
                Connection connection = null;
                field = fields.get(UUID.fromString(commands[1]));
                for (Connection con : connections.values()){
                    if (con.destField == field.fieldID && con.sourceField == miningMachine.fieldID){
                        connection = con;
                        break;
                    }
                }

                if (connection == null)
                    return false;

                if (miningMachine.posY == connection.sourceY && miningMachine.posX == connection.sourceX
                    && !fields.get(connection.sourceField).map[connection.destX][connection.destY].busy){
                    fields.get(connection.destField).map[connection.destY][connection.destX].busy = true;
                    fields.get(connection.destField).map[connection.sourceY][connection.sourceX].busy = false;
                    miningMachine.posX = connection.destX;
                    miningMachine.posY = connection.destY;
                    miningMachine.fieldID = connection.destField;
                    return true;
                } else
                    return false;

            default:
                field = fields.get(miningMachine.fieldID);
                miningMachine.move(commands,field);
                return true;
        }
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        if (miningMachineId == null)
            throw new IllegalArgumentException();
        return miningMachines.get(miningMachineId).fieldID;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        if (miningMachineId == null)
            throw new IllegalArgumentException();
        return miningMachines.get(miningMachineId).getCoordinates();
    }

    int determineRealY(int coordinateY, Field currentField){
        return Math.abs(coordinateY-(currentField.map.length-1));
    }
}