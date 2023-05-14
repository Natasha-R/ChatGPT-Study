package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TransportTechnology extends AbstractEntity {

    @Id
    private String technology;
    private UUID technologyID;

    @ElementCollection
    public List<Connection> connections;

    public TransportTechnology(String technology){
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public UUID getTechnologyID(){
        return this.technologyID;
    }

    public void setTechnologyID(UUID technologyID){
        this.technologyID = technologyID;
    }
}
