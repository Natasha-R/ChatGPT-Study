package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Field {

    private UUID id;
    private int height;
    private int width;
    private List<Barrier> HORIZONTAL;
    private List<Barrier> VERTICAL;
    private List<Connection> CONNECTION;

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
