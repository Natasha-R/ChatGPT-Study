package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class TransportSystem {
    @Id
    private UUID id;
    private String system;
    @OneToMany(targetEntity = Connection.class,  cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    private List<Connectable> connections;

    public TransportSystem(String system) {
        this.id = UUID.randomUUID();
        this.system = system;
        this.connections = new ArrayList<>();
    }

    public void addConnection(Connectable connection) {
        connections.add(connection);
    }

//        public Connectable fetchConnection(UUID sourceRoom, Coordinate sourcePosition, UUID destinationRoom) {
//        for (Connectable element : connections) {
//            if (element.equalsSource(sourceRoom, sourcePosition, destinationRoom)) {
//                return Optional.of(element);
//            }
//        }
//        return null;
//    }

}
