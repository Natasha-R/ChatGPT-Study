package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.entities.AbstractEntity;
import thkoeln.st.st2praktikum.entities.Connection;
import thkoeln.st.st2praktikum.entities.Room;
import thkoeln.st.st2praktikum.exceptions.TaskException;
import thkoeln.st.st2praktikum.interfaces.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class TidyUpRobot extends AbstractEntity implements GoAble, TransportAble, InitAble, HitAble {

    private String name;
    @ManyToOne
    private Room room;
    @Embedded
    private Point position;

    public TidyUpRobot(String name){
        this.name = name;
    }

    protected TidyUpRobot(){}

    @Override
    public Point go(Task task) {
        Point newPosition = position;
        for(int i = 0; i < task.getNumberOfSteps(); i++) {
            switch (task.getTaskType()) {
                case NORTH:
                    newPosition = new Point(position.getX(), position.getY() + 1);
                    break;
                case SOUTH:
                    if(position.getY() - 1 >= 0)
                        newPosition = new Point(position.getX(), position.getY() - 1);
                    break;
                case WEST:
                    if(position.getX() - 1 >= 0)
                        newPosition = new Point(position.getX() - 1, position.getY());
                    break;
                case EAST:
                    newPosition = new Point(position.getX() + 1, position.getY());
                    break;
                default:
                    throw new TaskException("Cant get in here.");
            }

            if(!room.isInsideLimit(newPosition))
                return position;

            if(!room.somethingIsHitByMove(position, newPosition))
                position = newPosition;
        }
        return position;
    }

    @Override
    public Boolean isHitByMove(Point sourcePoint, Point destinationPoint) {
        return destinationPoint.equals(position);
    }

    @Override
    public Boolean init(Room initialRoom) {
        if(getPoint() == null) {
            if(initialRoom.somethingIsHitByMove(new Point(0,0), new Point(0,0)))
                return false;
            room = initialRoom;
            position = new Point("(0,0)");
            room.addHitAble(this);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean transport(Room destinationRoom){
        for(UseAble useAble : room.getUseAbles()){
            if(useAble instanceof Connection){
                Connection connection = (Connection) useAble;
                if(connection.getSourcePoint().equals(position)){
                    if(connection.isUseable()) {
                        room.removeHitAble(this);
                        destinationRoom.addHitAble(this);
                        room = destinationRoom;
                        position = connection.getDestinationPoint();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getName(){
        return name;
    }

    public Point getPoint() {
        return position;
    }

    public UUID getRoomId() {
        return room.getUuid();
    }
}
