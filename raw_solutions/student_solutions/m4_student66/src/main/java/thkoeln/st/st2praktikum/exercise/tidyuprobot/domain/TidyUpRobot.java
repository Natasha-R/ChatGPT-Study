package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.core.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskException;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TidyUpRobot extends AbstractEntity implements GoAble, TransportAble, InitAble, HitAble {

    private String name;
    @ManyToOne
    private Room room;
    @Embedded
    private Point position;
    @Transient
    private List<Task> executedTasks = new ArrayList<>();

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
        addExecutedTask(task);
        return position;
    }

    @Override
    public Boolean isHitByMove(Point sourcePoint, Point destinationPoint) {
        return destinationPoint.equals(position);
    }

    @Override
    public Boolean init(Room initialRoom, Task task) {
        if(getPoint() == null) {
            if(initialRoom.somethingIsHitByMove(new Point(0,0), new Point(0,0)))
                return false;
            room = initialRoom;
            position = Point.fromString("(0,0)");
            room.addHitAble(this);
            addExecutedTask(task);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean transport(Room destinationRoom, Task task){
        for(UseAble useAble : room.getUseAbles()){
            if(useAble instanceof Connection){
                Connection connection = (Connection) useAble;
                if(connection.getSourcePoint().equals(position)){
                    if(connection.isUseable()) {
                        room.removeHitAble(this);
                        destinationRoom.addHitAble(this);
                        room = destinationRoom;
                        position = connection.getDestinationPoint();
                        addExecutedTask(task);
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

    public void setName(String name){ this.name = name; }

    public Point getPoint() {
        return position;
    }

    public UUID getRoomId() {
        return this.room == null ? null : this.room.getUuid();
    }

    public List<Task> getExecutedTasks(){
        return executedTasks;
    }

    public void addExecutedTask(Task task){
        executedTasks.add(task);
    }

    public void deleteExecutedTasks() {executedTasks = new ArrayList<>();}
}
