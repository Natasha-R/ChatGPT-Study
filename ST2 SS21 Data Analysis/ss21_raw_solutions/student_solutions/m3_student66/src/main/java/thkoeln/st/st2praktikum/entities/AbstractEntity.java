package thkoeln.st.st2praktikum.entities;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    private final UUID uuid;

    public AbstractEntity(){
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
