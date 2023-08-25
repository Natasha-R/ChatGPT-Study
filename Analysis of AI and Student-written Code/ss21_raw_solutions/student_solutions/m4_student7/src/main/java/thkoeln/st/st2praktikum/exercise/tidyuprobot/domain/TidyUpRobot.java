package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Blocking;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TidyUpRobot implements Blocking {
    @Id
    private UUID id = UUID.randomUUID();

    @Embedded
    private Vector2D vector2D;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Task.class)
    List<Task> tasks = new ArrayList<>();

    @OneToOne
    private Room room;

    private String name;


    public TidyUpRobot(String name){
        this.name = name;
    }

    public void move(Vector2D coordinates){
        this.vector2D = coordinates;
    }

    @Override
    public Boolean isBlockingCoordinate(Vector2D coordinates){
        if(this.vector2D.equals(coordinates))
            return true;
        return false;
    }

    public UUID getRoomId(){
        return room.getId();
    }
    public Vector2D getVector2D(){
        return vector2D;
    }

    public void addTask(Task task){
        tasks.add( task );
    }

}
