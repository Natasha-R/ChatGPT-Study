package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CleaningDevice {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID cleaningDeviceId;
    private String name;
    private Integer locationX;
    private Integer locationY;

    public CleaningDevice(String name) {
        this.name = name;
    }

    @Embedded
    private Coordinate coordinate;

    @Embedded
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "Space_ID")
    private Space space;


    @JsonIgnore
    public Coordinate getCoordinate() {
        return new Coordinate( this.locationX , this.locationY ) ;
    }

    public void setCoordinate( Integer locationX, Integer locationY ) {
        this.coordinate = new Coordinate( locationX , locationY );
    }

    @JsonIgnore
    public UUID getSpaceId() {
        return this.space.getSpaceId();
    }

    public void setTasks(Task task) {
        this.tasks.add( task );
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

}
