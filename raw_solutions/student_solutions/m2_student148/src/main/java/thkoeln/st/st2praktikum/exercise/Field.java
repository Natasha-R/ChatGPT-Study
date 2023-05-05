package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Field {
    private final UUID fieldId;
    private final Integer height, width;
    private ArrayList<Barrier> barrier = new ArrayList<>();

    public Field(Integer height, Integer width) {
        this.fieldId = UUID.randomUUID();
        this.height = height;
        this.width = width;
    }

    public UUID getId() {
        return this.fieldId;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public ArrayList<Barrier> getBarriers() {
        return barrier;
    }
}