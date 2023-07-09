package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class TransportTechnology {
    @Id
    @Getter
    private UUID id;
    @Getter
    private String type;

    public TransportTechnology(String type) {
        this.id = UUID.randomUUID();
        this.type = type;
    }
}
