package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TidyUpRobot implements Blockable {

    @Getter
    @Id
    private UUID id;

    @Getter
    @Setter
    private String name;
    @Getter
    @Embedded
    private Coordinate coordinate;

    @Getter
    @ManyToOne
    private Room room;

    @ElementCollection
    private List<Order> orderHistory;

    public void addOrdertoHistory(Order order){
        orderHistory.add(order);
    }

    public List<Order> getOrderHistory(){
        return orderHistory;
    }

    public void deleteOrderHistory(){
        orderHistory.clear();
    }

    public UUID getRoomId(){
        return this.room == null ? null : this.room.getId();
    }

    public TidyUpRobot(String name){
        id = UUID.randomUUID();
        room = null;
        this.name = name;
        coordinate = new Coordinate(0, 0);
        orderHistory = new ArrayList<>();
    }

    public TidyUpRobot(){
        id = UUID.randomUUID();
        room = null;
        this.name = null;
        coordinate = new Coordinate(0, 0);
    }

    public boolean executeRoomCommand(Room room, Order order){
        if(order.getOrderType() == OrderType.ENTER){
            return enterRoom(room, new Coordinate(0, 0));
        }else{
            return changeRoom(room);
        }
    }

    public void move(Order order){
        for(int i = 0; i < order.getNumberOfSteps(); i++){
            moveDirection(order.getOrderType());
        }
    }

     private void moveDirection(OrderType direction){
        switch(direction){
            case NORTH:
                if(room.canIGoThere(getNorthPosition()) &&
                        !room.obstacleCollision(getNorthPosition(), getNorthEastPosition())){
                    this.coordinate = getNorthPosition();
                }
                break;
            case EAST:
                if(room.canIGoThere(getEastPosition()) &&
                        !room.obstacleCollision(getEastPosition(), getNorthEastPosition())){
                    this.coordinate = getEastPosition();
                }
                break;
            case SOUTH:
                if(room.canIGoThere(getSouthPosition()) &&
                        !room.obstacleCollision(getCoordinate(), getEastPosition())){
                    this.coordinate = getSouthPosition();
                }
                break;
            case WEST:
                if(room.canIGoThere(getWestPosition()) &&
                        !room.obstacleCollision(getCoordinate(), getNorthPosition())){
                    this.coordinate = getWestPosition();
                }
                break;
            default:
        }
    }

     private boolean changeRoom(Room destinationRoom){
        if(room.validateConnectionPacket(coordinate, destinationRoom)) {
            Coordinate destinationCoordinate = room.getDestinationCoordinate(coordinate, destinationRoom);
            return enterRoom(destinationRoom, destinationCoordinate);
        }
        return false;
    }

     private boolean enterRoom(Room room, Coordinate coordinate){
        if(room.canIGoThere(coordinate)){
            leaveRoom();
            this.room = room;
            this.room.addRobot(this);
            this.coordinate = coordinate;
            return true;
        }
        return false;
    }

    private void leaveRoom(){
        if(room != null){
            room.removeRobot(this);
        }
    }

    private Coordinate getNorthPosition() {
        return new Coordinate(coordinate.getX(), coordinate.getY() + 1);
    }

    private Coordinate getEastPosition() {
        return new Coordinate(coordinate.getX() + 1,coordinate.getY());
    }

    private Coordinate getSouthPosition() {
        if(coordinate.getY() == 0) {
            return coordinate;
        }
        return new Coordinate(coordinate.getX(), coordinate.getY() - 1);
    }

    private Coordinate getWestPosition() {
        if(coordinate.getX() == 0) {
            return coordinate;
        }
        return new Coordinate(coordinate.getX() - 1, coordinate.getY());
    }

    private Coordinate getNorthEastPosition() {
        return new Coordinate(coordinate.getX() + 1, coordinate.getY() + 1);
    }

    public boolean positionBlocked(Coordinate coordinate) {
        return (this.coordinate.equals(coordinate));
    }

}