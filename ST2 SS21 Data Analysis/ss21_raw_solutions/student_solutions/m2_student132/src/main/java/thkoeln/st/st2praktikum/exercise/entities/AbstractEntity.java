package thkoeln.st.st2praktikum.exercise.entities;

import thkoeln.st.st2praktikum.exercise.interfaces.UUIDable;

import javax.persistence.Id;
import java.util.UUID;

public abstract class AbstractEntity implements UUIDable {

    @Id
    protected UUID id;

    protected AbstractEntity() {
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }
}
