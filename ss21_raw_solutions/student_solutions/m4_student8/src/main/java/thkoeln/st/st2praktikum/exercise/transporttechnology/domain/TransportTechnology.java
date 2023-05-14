package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class TransportTechnology
{
    @Id
    private UUID technologyID;

    private String type;

    @OneToMany
    private List<Connection> connections;

    public TransportTechnology(String type)
    {
        this.type = type;
        this.technologyID = UUID.randomUUID();
        this.connections = new ArrayList<>();
    }

    public void addConnection(Connection connection)
    {
        this.connections.add(connection);
    }

}
