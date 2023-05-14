package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.room.domain.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportTechnology
{
    @Id
    private UUID technologyID;
    private String name;

    @ElementCollection
    public List<Connection> connections;

    public TransportTechnology(String name)
    {
        this.technologyID = UUID.randomUUID();
        this.setName(name);
    }

    public UUID getTechnologyId()
    {
        return technologyID;
    }

    public void setTechnologyId(UUID technologyID)
    {
        this.technologyID = technologyID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
