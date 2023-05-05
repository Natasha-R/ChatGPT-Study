package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class MiningMachine extends AbstractEntity implements Movable, Blocking {
    private String name;
    @Embedded
    private Vector2D position;
    @OneToOne
    private Field mapOfTheMiningMachine;

    public MiningMachine(UUID miningMachineUUID,String name){
        this.name = name;
        id = miningMachineUUID;
    }

    @Override
    public void placeOnMap(Field map, Vector2D newPosition) {
        mapOfTheMiningMachine = map;
        position = newPosition;
    }

    @Override
    public boolean useConnection(Field desiredMap) {
        Connection connection = mapOfTheMiningMachine.getConnection(position, desiredMap.id);
        if(connection != null){
            if(desiredMap.addBlockingObject(connection.getDestinationPosition())){
                mapOfTheMiningMachine.removeBlocking(position);
                this.placeOnMap(desiredMap, connection.getDestinationPosition());
                return true;
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
        if(mapOfTheMiningMachine.removeBlocking(position)){
            Integer possibleSteps = mapOfTheMiningMachine.checkNorthDirection(position.getX(), position.getY(), steps);
            position = new Vector2D(position.getX(), position.getY() + possibleSteps);
            mapOfTheMiningMachine.addBlockingObject(position);
        }else throw new RuntimeException("MiningMachine not on Map");

    }
    private void moveEast(Integer steps){
        if(mapOfTheMiningMachine.removeBlocking(position)){
            Integer possibleSteps = mapOfTheMiningMachine.checkEastDirection(position.getX(), position.getY(), steps);
            position = new Vector2D(position.getX() + possibleSteps, position.getY());
            mapOfTheMiningMachine.addBlockingObject(position);
        }else throw new RuntimeException("MiningMachine not on Map");
    }
    private void moveSouth(Integer steps){
        if(mapOfTheMiningMachine.removeBlocking(position)){
            Integer possibleSteps = mapOfTheMiningMachine.checkSouthDirection(position.getX(), position.getY(), steps);
            position = new Vector2D(position.getX(), position.getY() - possibleSteps);
            mapOfTheMiningMachine.addBlockingObject(position);
        }else throw new RuntimeException("MiningMachine not on Map");
    }
    private void moveWest(Integer steps){
        if(mapOfTheMiningMachine.removeBlocking(position)){
            Integer possibleSteps = mapOfTheMiningMachine.checkWestDirection(position.getX(), position.getY(), steps);
            position = new Vector2D(position.getX() - possibleSteps, position.getY());
            mapOfTheMiningMachine.addBlockingObject(position);
        }else throw new RuntimeException("MiningMachine not on Map");
    }
    @Override
    public boolean isBlocked(Vector2D position) {
        return this.position.equals(position);
    }

    public UUID getFieldId(){
        return mapOfTheMiningMachine.id;
    }
    public Vector2D getVector2D(){
        return position;
    }
}
