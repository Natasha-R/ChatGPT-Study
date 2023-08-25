package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class MiningMachine implements Transportable, Initializable, Moveable {
    @Id
    private UUID id;
    //@GeneratedValue(strategy = GenerationType.AUTO)

    @Embedded
    private Point position = new Point(100,100);
    private String name ="noname";
    private UUID isLocatedOnField= null;

    @ElementCollection(targetClass = Task.class, fetch = FetchType.EAGER)
    List<Task> tasks = new ArrayList<>();

    public int getxPos() {
        return position.getX();
    }

    //public void setxPos(int xPos) {
    //2    this.position.setX(xPos);
    //}

    public int getyPos() {
        return position.getY();
    }
    public void moveToDir(int x, int y) {
        position = new Point(position.getX() + x,position.getY() + y);
    }
    // public void setyPos(int yPos) {
    //   this.position.setY(yPos);
    //}
    // public void setPos(Vector2d pos) {
    //   this.position = pos;
    // }
    public Point getPoint() {
        return position;
    }
    public String getStringPos() {
        return new String("("+position.getX()+","+position.getY()+")");
    }
    public String getName() {
        return name;
    }

    public void setName(String name, UUID miningMachine) {
        this.name = name;
        id = miningMachine;
    }

    public UUID getFieldId() {
        return isLocatedOnField;
    }

    public void setLocatedOnField(UUID isLocatedOnField) {
        this.isLocatedOnField = isLocatedOnField;
    }

    public MiningMachine(String name, UUID id)
    {
        this.name = name;
        this.id = id;
    }
    public void addTask(Task task)
    {
        tasks.add(task);
    }
    @Override
    public boolean transport(List<Connection> connections, UUID fieldUUID) {
        for(int i=0;i<connections.size();i++) {
            Connection connection = connections.get(i);
            if(connection.getDestinationFieldId().equals(fieldUUID))
            {
                if (isLocatedOnField != null && isLocatedOnField.equals(connection.getSourceFieldId()) && connection.getSourceCoordinate().equals(getStringPos())) {
                    setLocatedOnField(connection.getDestinationFieldId());
                    position = Point.string2Point(connection.getDestinationCoordinate());
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean initialize(UUID miningMachine, HashMap<UUID,MiningMachine> miningMachines, UUID fieldID) {
        if(cellIsEmpty(fieldID,new Point(0,0),miningMachines))
        {
            //miningMachines.get(miningMachineId).setLocatedOnField(fieldID);
            position = new Point(0,0);
            setLocatedOnField(fieldID);
            return true;
        }
        return false;
    }
    @Override
    public boolean move(Field field, Task task, HashMap<UUID,MiningMachine> miningMachines) {

        if (task.getTaskType() == TaskType.NORTH) {
            moveNorth(task.getNumberOfSteps(), field, miningMachines);
        }
        if (task.getTaskType() == TaskType.EAST) {
            moveEast(task.getNumberOfSteps(), field, miningMachines);
        }
        if (task.getTaskType() == TaskType.SOUTH) {
            moveSouth(task.getNumberOfSteps(), field, miningMachines);
        }
        if (task.getTaskType() == TaskType.WEST) {
            moveWest(task.getNumberOfSteps(), field, miningMachines);
        }
        return true;
    }
    @Override
    public void moveNorth(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines)
    {
        List<Obstacle> obstacles = field.getObstacles();

        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            Point mmPos = getPoint();
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.get(obstIndex);
                if(obstacle.getStart().getY() == obstacle.getEnd().getY() && obstacle.getStart().getX() <= mmPos.getX() && obstacle.getEnd().getX() > mmPos.getX() && obstacle.getStart().getY() == mmPos.getY() + 1)
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                Point moveTo = new Point(position.getX(), position.getY() + 1);
                for(Map.Entry miningMachine : miningMachines.entrySet()){
                    if(miningMachines.get(miningMachine.getKey()).getPoint().equals(moveTo))
                    {
                        throw new IllegalActionException("Field is already occupied!");
                    }
                }
                moveToDir(0, 1);
            }
        }
    }
    @Override
    public void moveEast(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines)
    {

        List<Obstacle> obstacles = field.getObstacles();
        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            Point mmPos = getPoint();
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.get(obstIndex);
                if(obstacle.getStart().getX() == obstacle.getEnd().getX() && obstacle.getStart().getY() <= mmPos.getY() && obstacle.getEnd().getY() > mmPos.getY() && obstacle.getStart().getX() - 1 == mmPos.getX())
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                Point moveTo = new Point(position.getX() + 1, position.getY());
                for(Map.Entry miningMachine : miningMachines.entrySet()){
                    if(miningMachines.get(miningMachine.getKey()).getPoint().equals(moveTo))
                    {
                        throw new IllegalActionException("Field is already occupied!");
                    }
                }
                moveToDir(1,0);
            }
        }
    }
    @Override
    public void moveSouth(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines)
    {
        List<Obstacle> obstacles = field.getObstacles();

        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            Point mmPos = getPoint();
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.get(obstIndex);
                if(obstacle.getStart().getY() == obstacle.getEnd().getY() && obstacle.getStart().getX() <= mmPos.getX() && obstacle.getEnd().getX() > mmPos.getX() && obstacle.getStart().getY() == mmPos.getY())
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                Point moveTo = new Point(position.getX(), position.getY() - 1);
                for(Map.Entry miningMachine : miningMachines.entrySet()){
                    if(miningMachines.get(miningMachine.getKey()).getPoint().equals(moveTo))
                    {
                        throw new IllegalActionException("Field is already occupied!");
                    }
                }
                moveToDir(0, -1);
            }
        }
    }
    @Override
    public void moveWest(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines) {

        List<Obstacle> obstacles = field.getObstacles();

        for (int i = 0; i < numberOfSteps; i++) {
            boolean moveBlocked = false;
            Point mmPos = getPoint();
            for (int obstIndex = 0; obstIndex < obstacles.size(); obstIndex++) {
                Obstacle obstacle = obstacles.get(obstIndex);
                if (obstacle.getStart().getX() == obstacle.getEnd().getX() && obstacle.getStart().getY() <= mmPos.getY() && obstacle.getEnd().getY() > mmPos.getY() && obstacle.getStart().getX() == mmPos.getX()) {
                    moveBlocked = true;
                }
            }
            if (!moveBlocked) {
                Point moveTo = new Point(position.getX() - 1, position.getY());
                for(Map.Entry miningMachine : miningMachines.entrySet()){
                    if(miningMachines.get(miningMachine.getKey()).getPoint().equals(moveTo))
                    {
                        throw new IllegalActionException("Feld is already occupied!");
                    }
                }
                moveToDir(-1, 0);
            }
        }
    }
    private boolean cellIsEmpty(UUID fieldID, Point pos, HashMap<UUID,MiningMachine> miningMachines)
    {
        for(Map.Entry<UUID, MiningMachine> entry : miningMachines.entrySet()) {
            MiningMachine value = entry.getValue();
            if(value.getFieldId() != null && value.getFieldId().equals(fieldID) && value.getxPos() == pos.getX() && value.getyPos() == pos.getY())
            {
                return false;
            }
        }
        return true;
    }
}
