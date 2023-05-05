package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine implements Movable, Blocking {
    UUID identifier;
    private String name;
    private Vector2D position;
    private Map mapOfTheMiningMachine;

    public MiningMachine(UUID miningMachineUUID,String name){
        this.name = name;
        identifier = miningMachineUUID;
    }

    @Override
    public void placeOnMap(Map map, Vector2D newPosition) {
        mapOfTheMiningMachine = map;
        position = newPosition;
    }

    @Override
    public boolean useConnection(Map desiredMap) {
        Connection connection = mapOfTheMiningMachine.getConnection(position);
        if(connection != null){
            if(connection.checkTargetMap(desiredMap.getUUID())) {
                if(desiredMap.addBlockingObject(this, connection.getDestinationPosition())){
                    mapOfTheMiningMachine.removeBlocking(this);
                    this.placeOnMap(desiredMap, connection.getDestinationPosition());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean move(Order moveCommand) {
        if(mapOfTheMiningMachine == null)return false;
        switch (moveCommand.getOrderType()){
            case NORTH: moveNorth(moveCommand.getNumberOfSteps());
                        break;
            case EAST:  moveEast(moveCommand.getNumberOfSteps());
                        break;
            case SOUTH: moveSouth(moveCommand.getNumberOfSteps());
                        break;
            case WEST:  moveWest(moveCommand.getNumberOfSteps());
                        break;
            default:    return false;
        }
        return true;
    }
    private void moveNorth(Integer steps){
        Integer possibleSteps = mapOfTheMiningMachine.checkNorthDirection(position.getX(), position.getY(), steps);
        position = new Vector2D(position.getX(), position.getY() + possibleSteps);
    }
    private void moveEast(Integer steps){
        Integer possibleSteps = mapOfTheMiningMachine.checkEastDirection(position.getX(), position.getY(), steps);
        position = new Vector2D(position.getX() + possibleSteps, position.getY());
    }
    private void moveSouth(Integer steps){
        Integer possibleSteps = mapOfTheMiningMachine.checkSouthDirection(position.getX(), position.getY(), steps);
        position = new Vector2D(position.getX(), position.getY() - possibleSteps);
    }
    private void moveWest(Integer steps){
        Integer possibleSteps = mapOfTheMiningMachine.checkWestDirection(position.getX(), position.getY(), steps);
        position = new Vector2D(position.getX() - possibleSteps, position.getY());
    }
    @Override
    public boolean isBlocked(Vector2D position) {
        return this.position.equals(position);
    }

    public Map getMap() {
        return mapOfTheMiningMachine;
    }

    public Vector2D getLocation() {
        return position;
    }
}
