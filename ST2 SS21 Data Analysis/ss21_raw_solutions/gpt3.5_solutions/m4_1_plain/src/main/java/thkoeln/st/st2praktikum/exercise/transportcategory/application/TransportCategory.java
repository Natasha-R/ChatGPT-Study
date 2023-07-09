package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import java.util.Objects;
import java.util.UUID;

public class TransportCategory {
    private UUID id;
    private String name;

    public TransportCategory(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportCategory)) return false;
        TransportCategory that = (TransportCategory) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
