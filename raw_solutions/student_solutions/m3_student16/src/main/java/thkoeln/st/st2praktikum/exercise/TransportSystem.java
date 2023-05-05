package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class TransportSystem {
    @Id
    private final UUID id = UUID.randomUUID();
    private String system;

    public TransportSystem(String system){
        this.system = system;
    }

    public TransportSystem() {
    }
}
