package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
public class TransportCategory {

    @Id
    @Getter
    private UUID id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    @ManyToMany (cascade = CascadeType.ALL)
    private List<Connection> connections = new ArrayList<>();


    public TransportCategory() {
        this.id = UUID.randomUUID();
    }

    public TransportCategory(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }


    public Optional<Connection> fetchConnection(UUID sourceRoom, Point sourcePosition, UUID destinationRoom) {
        for (Connection element : connections) {
            if (element.equalsSource(sourceRoom, sourcePosition, destinationRoom)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }
}
