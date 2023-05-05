package thkoeln.st.st2praktikum.exercise.entities;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Space {
    @Id
    private UUID id = UUID.randomUUID();

    private int width;
    private int height;

    @OneToMany
    @EqualsAndHashCode.Exclude
    private Set<CleaningDevice> cleaningDevices = new HashSet<>();

    @ElementCollection(targetClass = Barrier.class, fetch = FetchType.EAGER)
    private Set<Barrier> barriers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<SpaceConnection> spaceConnections = new HashSet<>();

    public Space() { }
}
