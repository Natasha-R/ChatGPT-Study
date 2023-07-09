package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public abstract class AbstractEntity {
    protected UUID id;
    protected AbstractEntity() {
        this.id = UUID.randomUUID();
    }
}

