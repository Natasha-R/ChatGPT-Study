package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class MiningMachineService implements SearchInterface, AddToWorldInterface, ExecutingInterface{

    List<FieldInterface> fields = new ArrayList<FieldInterface>();
    List<ConnectionInterface> connections = new ArrayList<ConnectionInterface>();
    List<MiningMachineInterface> miningMachines = new ArrayList<MiningMachineInterface>();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        FieldInterface temp = new Field(height, width);
        fields.add(temp);
        return temp.getId();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID fieldId, String wallString) {
        findField(fieldId).addWall(wallString);
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
        ConnectionInterface temp = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        connections.add(temp);
        return temp.getId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachineInterface temp = new MiningMachine(name);
        miningMachines.add(temp);
        return temp.getId();
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
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        char[] command= commandString.toCharArray();
        String instruction = "" + command[1] + command[2];

        String temp = "";

        for(int i = 4; i<=command.length-2; i++){
            temp += command[i];
        }


        switch(instruction){
            case "en":
                boolean fieldExists = false;
                for(int i = 0; i < fields.size(); i++){

                    if(temp.equals(fields.get(i).getId().toString())){
                        fieldExists = true;
                    }
                }
                boolean tileFree = true;
                for(int i = 0; i < miningMachines.size(); i++){
                    if(temp.equals(miningMachines.get(i).getMiningMachine().fieldId) && miningMachines.get(i).getMiningMachine().currentPos.x == 0 && miningMachines.get(i).getMiningMachine().currentPos.y == 0){
                        tileFree = false;
                    }
                }
                if(fieldExists && tileFree){
                    findMiningMachine(miningMachineId).currentPos = new Coordinate(0,0);
                    findMiningMachine(miningMachineId).fieldId = temp;
                    return true;
                }else{
                    return false;
                }
            case "no":
                Field act = null;
                for(int i = 0; i < fields.size(); i++){
                    if(findMiningMachine(miningMachineId).fieldId.equals(fields.get(i).getId().toString())){
                        act = fields.get(i).getField();
                    }
                }
                int schritte = Integer.parseInt(temp);
                Coordinate pos = findMiningMachine(miningMachineId).currentPos;
                boolean wall = false;
                    for(int i = 0; i < schritte; i++){
                        wall = false;
                        if(pos.y+1 < act.height) {
                            if (act.walls.size() > 0) {
                                for (int j = 0; j < act.walls.size(); j++) {
                                    act.stringToCoordinate(act.walls.get(j));
                                    if (act.wallstart.y == act.wallend.y) {
                                        if (act.wallstart.x <= pos.x && pos.x < act.wallend.x && pos.y + 1 == act.wallstart.y) {
                                            wall = true;
                                        }
                                    }
                                }
                            }
                            boolean occupied = false;
                            for(int k = 0; k < miningMachines.size(); k++){
                                if(miningMachines.get(k).getMiningMachine().currentPos != null && findMiningMachine(miningMachineId).currentPos.x == miningMachines.get(k).getMiningMachine().currentPos.x && findMiningMachine(miningMachineId).currentPos.y + 1 == miningMachines.get(k).getMiningMachine().currentPos.y){
                                    occupied = true;
                                }
                            }
                            if(!wall && !occupied){
                                pos.y += 1;
                            }
                        }
                    }
                return true;
            case "ea":
                act = null;
                for(int i = 0; i < fields.size(); i++){
                    if(findMiningMachine(miningMachineId).fieldId.equals(fields.get(i).getId().toString())){
                        act = fields.get(i).getField();
                    }
                }
                schritte = Integer.parseInt(temp);
                pos = findMiningMachine(miningMachineId).currentPos;
                wall = false;
                for(int i = 0; i < schritte; i++){
                    wall = false;
                    if(pos.x+1 < act.width) {
                        if (act.walls.size() > 0) {
                            for (int j = 0; j < act.walls.size(); j++) {
                                act.stringToCoordinate(act.walls.get(j));
                                if (act.wallstart.x == act.wallend.x) {
                                    if (act.wallstart.y <= pos.y && pos.y < act.wallend.y && pos.x + 1 == act.wallstart.x) {
                                        wall = true;
                                    }
                                }
                            }
                        }
                        boolean occupied = false;
                        for(int k = 0; k < miningMachines.size(); k++){
                            if(miningMachines.get(k).getMiningMachine().currentPos != null && findMiningMachine(miningMachineId).currentPos.x + 1 == miningMachines.get(k).getMiningMachine().currentPos.x && findMiningMachine(miningMachineId).currentPos.y == miningMachines.get(k).getMiningMachine().currentPos.y){
                                occupied = true;
                            }
                        }
                        if(!wall && !occupied){
                            pos.x += 1;
                        }
                    }
                }
                return true;
            case "so":
                act = null;
                for(int i = 0; i < fields.size(); i++){
                    if(findMiningMachine(miningMachineId).fieldId.equals(fields.get(i).getId().toString())){
                        act = fields.get(i).getField();
                    }
                }
                schritte = Integer.parseInt(temp);
                pos = findMiningMachine(miningMachineId).currentPos;
                wall = false;
                for(int i = 0; i < schritte; i++){
                    wall = false;
                    if(pos.y-1 >= 0) {
                        if (act.walls.size() > 0) {
                            for (int j = 0; j < act.walls.size(); j++) {
                                act.stringToCoordinate(act.walls.get(j));
                                if (act.wallstart.y == act.wallend.y) {
                                    if (act.wallstart.x <= pos.x && pos.x < act.wallend.x && pos.y == act.wallstart.y) {
                                        wall = true;
                                    }
                                }
                            }
                        }
                        boolean occupied = false;
                        for(int k = 0; k < miningMachines.size(); k++){
                            if(miningMachines.get(k).getMiningMachine().currentPos != null && findMiningMachine(miningMachineId).currentPos.x == miningMachines.get(k).getMiningMachine().currentPos.x && findMiningMachine(miningMachineId).currentPos.y - 1 == miningMachines.get(k).getMiningMachine().currentPos.y){
                                occupied = true;
                            }
                        }
                        if(!wall && !occupied){
                            pos.y -= 1;
                        }
                    }
                }
                return true;
            case "we":
                act = null;
                for(int i = 0; i < fields.size(); i++){
                    if(findMiningMachine(miningMachineId).fieldId.equals(fields.get(i).getId().toString())){
                        act = fields.get(i).getField();
                    }
                }
                schritte = Integer.parseInt(temp);
                pos = findMiningMachine(miningMachineId).currentPos;
                wall = false;
                for(int i = 0; i < schritte; i++){
                    wall = false;
                    if(pos.x-1 >= 0) {
                        if (act.walls.size() > 0) {
                            for (int j = 0; j < act.walls.size(); j++) {
                                act.stringToCoordinate(act.walls.get(j));
                                if (act.wallstart.x == act.wallend.x) {
                                    if (act.wallstart.y <= pos.y && pos.y < act.wallend.y && pos.x == act.wallstart.x) {
                                        wall = true;
                                    }
                                }
                            }
                        }
                        boolean occupied = false;
                        for(int k = 0; k < miningMachines.size(); k++){
                            if(miningMachines.get(k).getMiningMachine().currentPos != null && findMiningMachine(miningMachineId).currentPos.x-1 == miningMachines.get(k).getMiningMachine().currentPos.x && findMiningMachine(miningMachineId).currentPos.y == miningMachines.get(k).getMiningMachine().currentPos.y){
                                occupied = true;
                            }
                        }
                        if(!wall && !occupied){
                            pos.x -= 1;
                        }
                    }
                }
                return true;
            case "tr":
                    for(int i = 0; i < connections.size(); i++) {
                        if (connections.get(i).getConnection().destinationFieldId.toString().equals(temp) && connections.get(i).getConnection().sourceFieldId.toString().equals(findMiningMachine(miningMachineId).fieldId) && connections.get(i).getConnection().sourceCoordinate.x == findMiningMachine(miningMachineId).currentPos.x && connections.get(i).getConnection().sourceCoordinate.y == findMiningMachine(miningMachineId).currentPos.y) {
                            findMiningMachine(miningMachineId).currentPos = connections.get(i).getConnection().destinationCoordinate;
                            findMiningMachine(miningMachineId).fieldId = connections.get(i).getConnection().destinationFieldId.toString();
                            return true;
                        }
                    }
            default:
                return false;


        }
    }

    public FieldInterface findField(UUID fieldId){
        for(int i = 0; i<fields.size(); i++){
            if(fields.get(i).getId() == fieldId){
                return fields.get(i);
            }
        }
        throw new NullPointerException();
    }
    public MiningMachine findMiningMachine(UUID miningMachineId){
        for(int i = 0; i<miningMachines.size(); i++){
            if(miningMachines.get(i).getId() == miningMachineId){
                return miningMachines.get(i).getMiningMachine();
            }
        }
        throw new NullPointerException();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for(int i = 0; i<fields.size(); i++){
            if(findMiningMachine(miningMachineId).fieldId.equals(fields.get(i).getId().toString())){
                return fields.get(i).getId();
            }
        }
        throw new NullPointerException();
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        String result = "(" + findMiningMachine(miningMachineId).currentPos.x + "," + findMiningMachine(miningMachineId).currentPos.y + ")";
       return result;
    }
}