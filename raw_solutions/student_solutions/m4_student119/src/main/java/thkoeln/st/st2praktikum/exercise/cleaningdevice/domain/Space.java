package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
