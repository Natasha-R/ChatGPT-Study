package thkoeln.st.st2praktikum.exercise;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.entities.Space;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class CleaningDevice {
    @Id
    private UUID id = UUID.randomUUID();

    public CleaningDevice() { }

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Space space;

    @ElementCollection(targetClass = Command.class, fetch = FetchType.EAGER)
    private Set<Command> commands = new HashSet<>();

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
