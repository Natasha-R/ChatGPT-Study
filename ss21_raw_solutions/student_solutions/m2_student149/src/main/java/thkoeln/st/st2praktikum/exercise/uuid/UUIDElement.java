package thkoeln.st.st2praktikum.exercise.uuid;

import java.util.Objects;
import java.util.UUID;

public abstract class UUIDElement {

    private final UUID uuid = UUID.randomUUID();

    public final UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UUIDElement that = (UUIDElement) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + uuid;
    }
}
