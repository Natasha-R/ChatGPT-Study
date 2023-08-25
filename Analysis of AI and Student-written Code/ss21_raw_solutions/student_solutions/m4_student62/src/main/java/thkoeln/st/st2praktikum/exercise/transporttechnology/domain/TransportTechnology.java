package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class TransportTechnology {
    @Id
    private UUID technologyid;

    private static String Technology;
    @OneToMany
    private static List<Connector> connectors;

    public TransportTechnology (String TechnologyName) {
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
