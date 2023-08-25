package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class MiningMachineService implements SearchInterface, AddToWorldInterface, ExecutingInterface{

    private List<Field> fields = new ArrayList<Field>();
    private List<Connection> connections = new ArrayList<Connection>();
    private List<MiningMachine> miningMachines = new ArrayList<MiningMachine>();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field temp = new Field(height, width);
        fields.add(temp);
        return temp.getId();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */

    public void addWall(UUID fieldId, Wall wall) {
        Field temp = findField(fieldId);
        if(wall.getStart().getX() >= 0 && wall.getStart().getY() >= 0 && wall.getEnd().getX() <= temp.getWidth() && wall.getEnd().getY() <= temp.getHeight()){
            temp.addWall(wall);
        }else{
            throw new RuntimeException("wall out of bounds");
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
    public UUID addConnection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate) {
        Connection temp = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        connections.add(temp);
        return temp.getId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine temp = new MiningMachine(name);
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
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        switch(task.getTaskType()){
            case ENTER:
                boolean fieldExists = isFieldExisting(task.getGridId().toString());
                boolean tileFree = isTileFree(task.getGridId().toString());
                if(fieldExists && tileFree){
                    findMiningMachine(miningMachineId).setCurrentPos(new Point(0,0));
                    findMiningMachine(miningMachineId).setFieldId(task.getGridId().toString());
                    return true;
                }else{
                    return false;
                }
            case NORTH:
                walkNorth(miningMachineId, task.getNumberOfSteps());
                return true;
            case EAST:
                walkEast(miningMachineId, task.getNumberOfSteps());
                return true;
            case SOUTH:
                walkSouth(miningMachineId, task.getNumberOfSteps());
                return true;
            case WEST:
                walkWest(miningMachineId, task.getNumberOfSteps());
                return true;
            case TRANSPORT:
                for(int i = 0; i < connections.size(); i++) {
                    if (connections.get(i).getConnection().getDestinationFieldId().toString().equals(task.getGridId().toString()) && connections.get(i).getConnection().getSourceFieldId().toString().equals(findMiningMachine(miningMachineId).getFieldId()) && connections.get(i).getConnection().getSourceCoordinate().getX() == findMiningMachine(miningMachineId).getCurrentPos().getX() && connections.get(i).getConnection().getSourceCoordinate().getY() == findMiningMachine(miningMachineId).getCurrentPos().getY()) {
                        findMiningMachine(miningMachineId).setCurrentPos(connections.get(i).getConnection().getDestinationCoordinate());
                        findMiningMachine(miningMachineId).setFieldId(connections.get(i).getConnection().getDestinationFieldId().toString());
                        return true;
                    }
                }
            default:
                return false;


        }
    }
    private void walkNorth(UUID miningMachineId,int schritte){
        Field act = findFieldByMiningMachineId(miningMachineId);
        Point pos;
        boolean wall = false;
        for(int i = 0; i < schritte; i++){
            pos = findMiningMachine(miningMachineId).getCurrentPos();
            wall = false;
            if(pos.getY()+1 < act.getHeight()) {
                if (act.getWalls().size() > 0) {
                    for (int j = 0; j < act.getWalls().size(); j++) {
                        if (act.getWalls().get(j).getStart().getY() == act.getWalls().get(j).getEnd().getY()) {
                            if (act.getWalls().get(j).getStart().getX() <= pos.getX() && pos.getX() < act.getWalls().get(j).getEnd().getX() && pos.getY() + 1 == act.getWalls().get(j).getStart().getY()) {
                                wall = true;
                            }
                        }
                    }
                }
                boolean occupied = false;
                for(int k = 0; k < miningMachines.size(); k++){
                    if(miningMachines.get(k).getMiningMachine().getCurrentPos() != null && findMiningMachine(miningMachineId).getCurrentPos().getX() == miningMachines.get(k).getMiningMachine().getCurrentPos().getX() && findMiningMachine(miningMachineId).getCurrentPos().getY() + 1 == miningMachines.get(k).getMiningMachine().getCurrentPos().getY()){
                        occupied = true;
                    }
                }
                if(!wall && !occupied){
                    findMiningMachine(miningMachineId).setCurrentPos(new Point(pos.getX(), pos.getY()+1));
                }
            }
        }
    }

    private void walkEast(UUID miningMachineId,int schritte){
        Field act = findFieldByMiningMachineId(miningMachineId);
        Point pos;
        Boolean wall = false;
        for(int i = 0; i < schritte; i++){
            pos = findMiningMachine(miningMachineId).getCurrentPos();
            wall = false;
            if(pos.getX()+1 < act.getWidth()) {
                if (act.getWalls().size() > 0) {
                    for (int j = 0; j < act.getWalls().size(); j++) {
                        if (act.getWalls().get(j).getStart().getX() == act.getWalls().get(j).getEnd().getX()) {
                            if (act.getWalls().get(j).getStart().getY() <= pos.getY() && pos.getY() < act.getWalls().get(j).getEnd().getY() && pos.getX() + 1 == act.getWalls().get(j).getStart().getX()) {
                                wall = true;
                            }
                        }
                    }
                }
                boolean occupied = false;
                for(int k = 0; k < miningMachines.size(); k++){
                    if(miningMachines.get(k).getMiningMachine().getCurrentPos() != null && findMiningMachine(miningMachineId).getCurrentPos().getX() + 1 == miningMachines.get(k).getMiningMachine().getCurrentPos().getX() && findMiningMachine(miningMachineId).getCurrentPos().getY() == miningMachines.get(k).getMiningMachine().getCurrentPos().getY()){
                        occupied = true;
                    }
                }
                if(!wall && !occupied){
                    findMiningMachine(miningMachineId).setCurrentPos(new Point(pos.getX()+1, pos.getY()));
                    
                }
            }
        }
    }

    private void walkSouth(UUID miningMachineId, int schritte){
        Field act = findFieldByMiningMachineId(miningMachineId);
        Point pos;
        Boolean wall = false;
        for(int i = 0; i < schritte; i++){
            pos = findMiningMachine(miningMachineId).getCurrentPos();
            wall = false;
            if(pos.getY()-1 >= 0) {
                if (act.getWalls().size() > 0) {
                    for (int j = 0; j < act.getWalls().size(); j++) {
                        if (act.getWalls().get(j).getStart().getY() == act.getWalls().get(j).getEnd().getY()) {
                            if (act.getWalls().get(j).getStart().getX() <= pos.getX() && pos.getX() < act.getWalls().get(j).getEnd().getX() && pos.getY() == act.getWalls().get(j).getStart().getY()) {
                                wall = true;
                            }
                        }
                    }
                }
                boolean occupied = false;
                for(int k = 0; k < miningMachines.size(); k++){
                    if(miningMachines.get(k).getMiningMachine().getCurrentPos() != null && findMiningMachine(miningMachineId).getCurrentPos().getX() == miningMachines.get(k).getMiningMachine().getCurrentPos().getX() && findMiningMachine(miningMachineId).getCurrentPos().getY() - 1 == miningMachines.get(k).getMiningMachine().getCurrentPos().getY()){
                        occupied = true;
                    }
                }
                if(!wall && !occupied){
                    findMiningMachine(miningMachineId).setCurrentPos(new Point(pos.getX(), pos.getY()-1));
                }
            }
        }
    }
    private void walkWest(UUID miningMachineId, int schritte){
        Field act = findFieldByMiningMachineId(miningMachineId);
        Point pos;
        Boolean wall = false;
        for(int i = 0; i < schritte; i++){
            pos = findMiningMachine(miningMachineId).getCurrentPos();
            wall = false;
            if(pos.getX()-1 >= 0) {
                if (act.getWalls().size() > 0) {
                    for (int j = 0; j < act.getWalls().size(); j++) {
                        if (act.getWalls().get(j).getStart().getX() == act.getWalls().get(j).getEnd().getX()) {
                            if (act.getWalls().get(j).getStart().getY() <= pos.getY() && pos.getY() < act.getWalls().get(j).getEnd().getY() && pos.getX() == act.getWalls().get(j).getStart().getX()) {
                                wall = true;
                            }
                        }
                    }
                }
                boolean occupied = false;
                for(int k = 0; k < miningMachines.size(); k++){
                    if(miningMachines.get(k).getMiningMachine().getCurrentPos() != null && findMiningMachine(miningMachineId).getCurrentPos().getX()-1 == miningMachines.get(k).getMiningMachine().getCurrentPos().getX() && findMiningMachine(miningMachineId).getCurrentPos().getY() == miningMachines.get(k).getMiningMachine().getCurrentPos().getY()){
                        occupied = true;
                    }
                }
                if(!wall && !occupied){
                    findMiningMachine(miningMachineId).setCurrentPos(new Point(pos.getX()-1, pos.getY()));
                }
            }
        }
    }

    private Boolean isFieldExisting(String temp){
        for(int i = 0; i < fields.size(); i++){

            if(temp.equals(fields.get(i).getId().toString())){
                return true;
            }
        }
        return false;
    }
    private Boolean isTileFree(String temp){
        for(int i = 0; i < miningMachines.size(); i++){
            if(temp.equals(miningMachines.get(i).getMiningMachine().getFieldId()) && miningMachines.get(i).getMiningMachine().getCurrentPos().getX() == 0 && miningMachines.get(i).getMiningMachine().getCurrentPos().getY() == 0){
                return false;
            }
        }
        return true;
    }
    private Field findFieldByMiningMachineId(UUID miningMachineId){
        for(int i = 0; i < fields.size(); i++) {
            if (findMiningMachine(miningMachineId).getFieldId().equals(fields.get(i).getId().toString())) {
                return fields.get(i).getField();
            }
        }
        throw new NullPointerException();
    }

    private Field findField(UUID fieldId){
        for(int i = 0; i<fields.size(); i++){
            if(fields.get(i).getId() == fieldId){
                return fields.get(i);
            }
        }
        throw new NullPointerException();
    }
    private MiningMachine findMiningMachine(UUID miningMachineId){
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
            if(findMiningMachine(miningMachineId).getFieldId().equals(fields.get(i).getId().toString())){
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
    public Point getPoint(UUID miningMachineId){
        return findMiningMachine(miningMachineId).getCurrentPos();
    }
}
