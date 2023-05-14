package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@NoArgsConstructor
public class TransportTechnology {
    @Id
    private UUID transportId;

    private String technology;

    @OneToMany
    private List<Connector> connectors;



    public TransportTechnology(String technology) {
        this.transportId=UUID.randomUUID();
        this.technology=technology;
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




