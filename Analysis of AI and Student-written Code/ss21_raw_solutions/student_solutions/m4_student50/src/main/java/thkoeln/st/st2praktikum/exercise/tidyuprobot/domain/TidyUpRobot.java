package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.Moveable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.Wall;

import javax.persistence.*;
import java.util.*;
//import static thkoeln.st.st2praktikum.exercise.Zone.fetchRoomWithId;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TidyUpRobot extends AbstractEntity implements Moveable {

    private String name;

    @Embedded
    protected Coordinate coordinate;

    protected UUID roomUUID;

    @OneToOne
    public Room currentRoom;

    public Coordinate getCoordinate(){
        return this.coordinate;
    }

    @ElementCollection(targetClass = Order.class)
    private List<Order> orders = new ArrayList<Order>();

    public UUID getRoomId(){
        return this.roomUUID;
    }

    public void deleteCommands(){
        orders.clear();
    }

    public void addCommand(Order order){
        orders.add(order);
    }

   /* public void setXY(int x, int y){
        Coordinate coordinate = new Coordinate(x,y);
        this.coordinate = coordinate;
    }*/

    @Override
    public void moveEast(int step) {
        int xValueOfRobot = getCoordinate().getX() + step;
        //Is there another Robot in same path?
        Integer xValueOfAnotherRobot = null;

        for (int i = 0; i<getCurrentRoom().getTidyUpRobots().size(); i++){
            Coordinate coordinate = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate();
            if (coordinate.getY() == getCoordinate().getY() && getCoordinate().getX() < coordinate.getX()) {
                xValueOfAnotherRobot = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate().getX();
                break;
            }
        }

        if(xValueOfRobot >= currentRoom.getWidth()) xValueOfRobot = currentRoom.getWidth() - 1;

        if (currentRoom.walls.isEmpty()){
            if(xValueOfAnotherRobot != null && xValueOfRobot >= xValueOfAnotherRobot) xValueOfRobot = xValueOfAnotherRobot - 1;
        }
        for (int i = 0; i< currentRoom.walls.size(); i++){
            Wall wall = currentRoom.walls.get(i);
            if(wall.getStart().getX() == wall.getEnd().getX()){
                if(xValueOfAnotherRobot != null && xValueOfRobot >= xValueOfAnotherRobot) xValueOfRobot = xValueOfAnotherRobot - 1;
                else if (getCoordinate().getY() <= wall.getStart().getY() && getCoordinate().getY() > wall.getEnd().getY()){
                    if (xValueOfRobot >= wall.getStart().getX()) xValueOfRobot = wall.getStart().getX() - 1;
                }
            }
        }
        getCoordinate().setX(xValueOfRobot);
    }

    @Override
    public void moveNorth(int step) {
        int yValueOfRobot = getCoordinate().getY() + step;
        Integer yValueOfAnotherRobot = null;

        for (int i = 0; i<getCurrentRoom().getTidyUpRobots().size(); i++){
            Coordinate coordinate = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate();
            if (coordinate.getX() == getCoordinate().getX() && getCoordinate().getY() < coordinate.getY()) {
                yValueOfAnotherRobot = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate().getY(); break;
            }
        }

        if(yValueOfRobot >= currentRoom.getHeight()) yValueOfRobot = currentRoom.getHeight() - 1;

        if (currentRoom.walls.isEmpty()){
            if(yValueOfAnotherRobot != null && yValueOfRobot >= yValueOfAnotherRobot) yValueOfRobot = yValueOfAnotherRobot - 1;
        }
        for (int i = 0; i < currentRoom.walls.size(); i++) {
            Wall wall = currentRoom.walls.get(i);
            if (wall.getStart().getY() == wall.getEnd().getY()) {
                if (yValueOfAnotherRobot != null && yValueOfRobot >= yValueOfAnotherRobot) yValueOfRobot = yValueOfAnotherRobot - 1;
                else if (getCoordinate().getX() >= wall.getStart().getX() && getCoordinate().getX() < wall.getEnd().getX()) {
                    if (yValueOfRobot >= wall.getStart().getY()) yValueOfRobot = wall.getStart().getY() - 1;
                }
            }
        }
        getCoordinate().setY(yValueOfRobot);
    }

    @Override
    public void moveSouth(int step) {
        int yValueOfRobot = getCoordinate().getY() - step;
        Integer yValueOfAnotherRobot = null;

        for (int i = 0; i<getCurrentRoom().getTidyUpRobots().size(); i++){
            Coordinate coordinate = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate();
            if ((coordinate.getX()) == getCoordinate().getX() && getCoordinate().getY() > coordinate.getY()) {
                yValueOfAnotherRobot = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate().getY();
                break;
            }
        }

        if(yValueOfRobot < 0) yValueOfRobot = 0;
        if (currentRoom.walls.isEmpty()){
            if(yValueOfAnotherRobot != null && yValueOfRobot <= yValueOfAnotherRobot) yValueOfRobot = yValueOfAnotherRobot + 1;
        }

        for (int i = 0; i < currentRoom.walls.size(); i++){
            Wall wall = currentRoom.walls.get(i);
            if(wall.getStart().getY() == wall.getEnd().getY()){
                if(yValueOfAnotherRobot != null && yValueOfRobot <= yValueOfAnotherRobot) yValueOfRobot = yValueOfAnotherRobot + 1;
                else if (getCoordinate().getX() <= wall.getEnd().getX() && getCoordinate().getX() > wall.getEnd().getX()){
                    if (yValueOfRobot <= wall.getStart().getY()) yValueOfRobot = wall.getStart().getY() + 1;
                }
            }
        }
        getCoordinate().setY(yValueOfRobot);
    }

    @Override
    public void moveWest(int step) {
        int xValueOfRobot = getCoordinate().getX() - step;
        Integer xValueOfAnotherRobot = null;

        for (int i = 0; i<getCurrentRoom().getTidyUpRobots().size(); i++){
            Coordinate coordinate = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate();
            if (coordinate.getY() == getCoordinate().getY() && getCoordinate().getX() > coordinate.getX()) {
                xValueOfAnotherRobot = getCurrentRoom().getTidyUpRobots().get(i).getCoordinate().getX();
                break;
            }
        }

        if(xValueOfRobot < 0) xValueOfRobot = 0;
        if (currentRoom.walls.isEmpty()){
            if(xValueOfAnotherRobot != null && xValueOfRobot <= xValueOfAnotherRobot) xValueOfRobot = xValueOfAnotherRobot + 1;
        }

        for (int i = 0; i < currentRoom.walls.size(); i++){
            Wall wall = currentRoom.walls.get(i);
            if(wall.getStart().getX() == wall.getEnd().getX()){
                if(xValueOfAnotherRobot != null && xValueOfRobot <= xValueOfAnotherRobot) xValueOfRobot = xValueOfAnotherRobot+1;
                else if (getCoordinate().getY() >= wall.getStart().getY() && getCoordinate().getY() < wall.getEnd().getY()){
                    if (xValueOfRobot<=wall.getStart().getX()) xValueOfRobot = wall.getStart().getX() + 1;
                }
            }
        }
        getCoordinate().setX(xValueOfRobot);
    }

    public void setXY(int x, int y){
        Coordinate coordinate = new Coordinate(x,y);
        this.coordinate = coordinate;
    }
}
