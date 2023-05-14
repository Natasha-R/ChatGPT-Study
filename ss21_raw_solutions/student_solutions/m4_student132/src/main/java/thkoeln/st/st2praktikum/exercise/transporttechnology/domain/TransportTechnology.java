package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class TransportTechnology {

    @Id
    private final UUID technologyId = UUID.randomUUID();

    private String technologyName;

    public TransportTechnology(String technology) {
        this.technologyName = technology;
    }

    protected TransportTechnology() {
    }
}
