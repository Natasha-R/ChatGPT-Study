package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TransportSystem {

    @Id
    private UUID TransportSystemID;
    private String name;

    public TransportSystem(UUID transportSystemID, String name) {
        TransportSystemID = transportSystemID;
        this.name = name;
    }

    public UUID getTransportSystemID() {
        return TransportSystemID;
    }

    public String getName() {
        return name;
    }
}
