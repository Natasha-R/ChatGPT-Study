package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class CleaningDevice {
    @Id
    UUID cleaningDeviceId;

    UUID spaceId;
    Point point;
    String name;
    @ElementCollection(targetClass = Task.class, fetch = FetchType.EAGER)
    private final List<Task> tasks = new ArrayList<>();




    public CleaningDevice(String name) {
        this.cleaningDeviceId = UUID.randomUUID();
        this.name = name;
    }

    public void move(TaskType taskType){
        switch (taskType) {
            case NORTH: point.movNorth();break;
            case SOUTH:  point.movSouth();break;
            case EAST:  point.movEast();break;
            case WEST:  point.movWest();
        }
    }





}
