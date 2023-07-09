package thkoeln.st.st2praktikum.exercise.environment.transition;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TransportTechnology {

    @Id
    private UUID uuid = UUID.randomUUID();

    private String name;

    public TransportTechnology(String name) {
        this.name = name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getId() {
        return uuid;
    }
}