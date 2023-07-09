package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;



public abstract class Entity {
    @Getter
    private final UUID id = UUID.randomUUID();

    public String toString() {
        return this.id.toString();
    }
}
