package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class TransportCategory {

    @Id
    @Getter
    private UUID id;
    @Getter
    private String name;

    public TransportCategory() {
        this.id = UUID.randomUUID();
    }

    public TransportCategory(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
