package thkoeln.st.st2praktikum.exercise.Robot;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Move.BasicMovement;
import thkoeln.st.st2praktikum.exercise.Move.Moveable;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.Room.Room;

@Setter
@Getter
public class TidyUpRobot extends Robot implements Blockable {

    private String name;
    private Moveable movement;
    private Coordinate coordinate;
    private Room room;

    public TidyUpRobot(String name){
        room = null;
        this.name = name;
        movement = new BasicMovement();
        coordinate = new Coordinate(0, 0);
    }

    public void move(Order order){
        coordinate = movement.move(order.getOrderType(), order.getNumberOfSteps(), coordinate, room);
    }

    public boolean enterRoom(Room room, Coordinate coordinate){
        if(room.canIGoThere(coordinate)){
            leaveRoom();
            this.room = room;
            this.room.addRobot(this);
            this.coordinate = coordinate;
            return true;
        }
        return false;
    }

    public boolean changeRoom(Room destinationRoom){
        if(room.validateConnectionPacket(coordinate, destinationRoom)) {
            Coordinate destinationCoordinate = room.getDestinationCoordinate(coordinate, destinationRoom);
            return enterRoom(destinationRoom, destinationCoordinate);
        }
        return false;
    }

    private void leaveRoom(){
        if(room != null){
            room.removeRobot(this);
        }
    }

    public boolean positionBlocked(Coordinate coordinate) {
        return (this.coordinate.equals(coordinate));
    }
}