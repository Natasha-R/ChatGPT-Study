package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Field {

    @Id
    private UUID id;
    private int height;
    private int width;
    @Transient
    private List<Barrier> HORIZONTAL;
    @Transient
    private List<Barrier> VERTICAL;
    @Transient
    private List<Connection> CONNECTION;

    public Field() {
        HORIZONTAL = new ArrayList<>();
        VERTICAL = new ArrayList<>();
        CONNECTION = new ArrayList<>();
    }

    public Field(UUID id, int height, int width) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.HORIZONTAL = new ArrayList<>();
        this.VERTICAL = new ArrayList<>();
        this.CONNECTION = new ArrayList<>();
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

    public List<Connection> getCONNECTION() {
        return CONNECTION;
    }
}
