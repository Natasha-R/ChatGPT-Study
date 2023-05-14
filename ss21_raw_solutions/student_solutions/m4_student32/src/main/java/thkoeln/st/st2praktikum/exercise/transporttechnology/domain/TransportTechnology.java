package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportTechnology {
    @Id
    private UUID id;
    private String technology;

    public TransportTechnology(UUID id, String technology) {
        this.id = id;
        this.technology = technology;
    }

    public UUID getId() {
        return id;
    }

    public String getTechnology() {
        return technology;
    }
}
