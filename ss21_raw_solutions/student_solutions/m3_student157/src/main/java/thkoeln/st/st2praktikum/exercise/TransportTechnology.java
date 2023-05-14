package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class TransportTechnology {

    private final String name;
    @Id
    private final UUID uuid;

    public TransportTechnology(String name)
    {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public TransportTechnology() {
        name = null;
        uuid = UUID.randomUUID();
    }
}
