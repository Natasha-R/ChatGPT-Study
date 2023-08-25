package thkoeln.st.st2praktikum.exercise.transporttechnology;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class TransportTechnology {

    @Id
    private final UUID transportTechnologyId = UUID.randomUUID();

    private String transportTechnologyName;

    public TransportTechnology(String transportTechnology) {
        this.transportTechnologyName = transportTechnology;
    }

    protected TransportTechnology() {
    }
}
