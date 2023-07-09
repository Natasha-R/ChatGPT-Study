package thkoeln.st.st2praktikum.entities;

import java.util.UUID;

public abstract class AbstractEntity {

    private UUID uuid;

    public AbstractEntity(){
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
