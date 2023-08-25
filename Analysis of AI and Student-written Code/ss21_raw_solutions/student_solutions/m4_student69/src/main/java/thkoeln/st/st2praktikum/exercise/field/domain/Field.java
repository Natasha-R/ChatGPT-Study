package thkoeln.st.st2praktikum.exercise.field.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.*;

@Entity
public class Field {

    @Id
    private UUID id;
    private int height;
    private int width;
    @Transient
    private List<Barrier> HORIZONTAL = new ArrayList<>();
    @Transient
    private List<Barrier> VERTICAL = new ArrayList<>();
    @Transient
    private Map<UUID, Connection> CONNECTION = new HashMap<>();

    public Field() {
    }

    public Field(UUID id, int height, int width) {
        this.id = id;
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Barrier> getHORIZONTAL() {
        return HORIZONTAL;
    }

    public List<Barrier> getVERTICAL() {
        return VERTICAL;
    }

    public UUID getId() {
        return id;
    }

    public Map<UUID, Connection> getCONNECTION() {
        return CONNECTION;
    }
}
