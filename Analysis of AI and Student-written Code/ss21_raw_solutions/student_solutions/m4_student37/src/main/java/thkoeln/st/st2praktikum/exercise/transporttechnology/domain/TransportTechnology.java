package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Setter
@Getter
public class TransportTechnology {
    @Id
    private UUID transportId;

    private static String technology;
    @OneToMany
    private static List<Connector> connectors;



    public TransportTechnology(String technologyName) {
        transportId=UUID.randomUUID();
        technology=technologyName;
        connectors = new ArrayList<>();
    }

    public void addConnector( Connector connector) {
        connectors.add(connector);
    }

    public UUID getTransportId() {
        return transportId;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }
}




