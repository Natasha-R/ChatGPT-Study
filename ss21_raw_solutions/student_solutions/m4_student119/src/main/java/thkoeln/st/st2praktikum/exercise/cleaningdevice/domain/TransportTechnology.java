package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
public class TransportTechnology {
    @Id
    private UUID id = UUID.randomUUID();

    private String technology;

    public TransportTechnology() {
    }
}
