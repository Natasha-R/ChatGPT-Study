package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.repository.SpaceRepository;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class CleaningDevice extends AbstractEntity {

    private String name;

    private Coordinate coordinate;

    private ArrayList<Task> taskList = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Space space;

    public static CleaningDevice fromShit(String name){
        return new CleaningDevice(name);
    }

    public CleaningDevice(String name) {
        this.name = name;
    }

    public CleaningDevice() {
    }

    public Boolean executeCommand(Task task, SpaceRepository spaceRepository){
        taskList.add(task);
        switch (task.getTaskType()){
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                move(task);
                System.out.println(coordinate.toString());
                return true;
            case TRANSPORT:
                return transport(task, spaceRepository);
            case ENTER:
                UUID spaceId = task.getGridId();
                Space givenSpace = spaceRepository.findById(spaceId).isPresent() ? spaceRepository.findById(spaceId).get() : null;
                if(givenSpace!= null && givenSpace.isDefaultPositionFree()) {
                    space = givenSpace;
                    place();
                    return true;
                }
                return false;
            default:
                throw new IllegalStateException("Should never be possible");
        }

    }


    public void move(Task task){
        Boolean moveAwayFromDefault = coordinate != null && coordinate.toString().equals(Coordinate.DEFAULT);
        int steps = task.getNumberOfSteps();
        while(steps>0){
            switch (task.getTaskType()){
                case NORTH:
                    if(space.horizontalTileFree(coordinate, 1)){
                        coordinate.incrementY();
                    } else {
                        steps=0;
                    }
                    break;
                case SOUTH:
                    if(space.horizontalTileFree(coordinate, -1)){
                        coordinate.decrementY();
                    } else {
                        steps=0;
                    }
                    break;
                case EAST:
                    if(space.verticalTileFree(coordinate, 1)){
                        coordinate.incrementX();
                    } else {
                        steps=0;
                    }
                    break;
                case WEST:
                    if(space.verticalTileFree(coordinate, -1)){
                        coordinate.decrementX();
                    } else {
                        steps=0;
                    }
                    break;
            }
            steps--;
        }
        if(moveAwayFromDefault){
            space.setDefaultPositionFree(true);
        }
        if(coordinate.toString().equals(Coordinate.DEFAULT)){
            space.setDefaultPositionFree(false);
        }
    }

    private boolean transport(Task task, SpaceRepository spaceRepository){
        UUID spaceId = task.getGridId();
        for (Connection connection : space.getConnections()){
            if (connection.getDestinationSpaceId().equals(spaceId) && connection.getSourceSpaceCoordinate().equals(getCoordinate())){
                place(connection.getDestinationSpaceCoordinate());
                space = spaceRepository.findById(connection.getDestinationSpaceId()).get();
                return true;
            }
        }
        return false;
    }

    private void place(){
        place("(0,0)");
        space.setDefaultPositionFree(false);
    }

    private void place(String position){
        this.coordinate = new Coordinate(position);
    }

    private void place(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    @JsonIgnore
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @JsonIgnore
    public UUID getSpaceId(){
        if(space!=null){
            return space.getId();
        }
        return null;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void deleteTasks(){
        taskList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Task> getAllTasks(){
        return taskList;
    }
}
