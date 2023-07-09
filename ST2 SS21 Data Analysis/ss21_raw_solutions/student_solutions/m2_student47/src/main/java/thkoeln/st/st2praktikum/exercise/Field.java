package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;


@Getter
public class Field {
    private final UUID fieldID;
    private final int width;
    private final int height;
    private final ArrayList<Barrier> Barriers = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();

    public Field(int width, int height) {
        this.height = height;
        this.width = width;
        this.fieldID = UUID.randomUUID();
    }
}
