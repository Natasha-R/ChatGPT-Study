package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.*;
import java.util.*;


@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class CleaningDevice {
    @Id
    private UUID id = UUID.randomUUID();;

    public CleaningDevice() { }

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Space space;

    @ElementCollection(targetClass = Command.class, fetch = FetchType.EAGER)
    private List<Command> commands = new ArrayList<>();

    @Transient
    public UUID getSpaceId() {
        if(space == null)
            return null;

        return space.getId();
    }

    private String name;

    @Embedded
    private Point point;
}
