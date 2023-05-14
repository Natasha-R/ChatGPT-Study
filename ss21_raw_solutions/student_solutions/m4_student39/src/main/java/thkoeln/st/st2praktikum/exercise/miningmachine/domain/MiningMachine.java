package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class MiningMachine extends AbstractEntity implements Movable, Blocking {
    @Setter
    private String name;
    @Embedded
    private Vector2D position;
    @Setter
    @ElementCollection( targetClass = Order.class, fetch = FetchType.EAGER)
    private  List<Order> listOfOrders = new ArrayList<>();
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
        Connection connection = mapOfTheMiningMachine.getConnection(position, desiredMap.getId());
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
        return mapOfTheMiningMachine.getId();
    }
    public Vector2D getVector2D(){
        return position;
    }
}