package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.*;

@Entity
@NoArgsConstructor
public class Field {
    @Id
    private UUID id;
    private int start;
    private int end;
    @Transient
    private List<SingleWall> verticalWalls = new ArrayList<>();
    @Transient
    private List<SingleWall> horizontalWalls = new ArrayList<>();
    @Transient
    private Map<UUID, FieldConnection> fieldConnections = new HashMap<>();

    public Field(UUID uuid, int start, int end) {
        this.id = uuid;
        this.start = start;
        this.end = end;
    }

    public UUID getId() {
        return id;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public List<SingleWall> getHorizontalWalls() {
        return horizontalWalls;
    }

    public List<SingleWall> getVerticalWalls() {
        return verticalWalls;
    }

    public Map<UUID, FieldConnection> getFieldConnections() {
        return fieldConnections;
    }
}
