package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TransportTechnology {
    @Id
    private UUID technologyid;

    private String Technology;
    @OneToMany
    private List<Connector> connectors;

    public TransportTechnology(String TechnologyName) {
        technologyid = UUID.randomUUID();
        Technology = TechnologyName;
        connectors = new ArrayList<>();
    }

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    public UUID getTechnologyId() {
        return technologyid;
    }

    public List<Connector> getConnectors()
    {
        return connectors;
    }
}
