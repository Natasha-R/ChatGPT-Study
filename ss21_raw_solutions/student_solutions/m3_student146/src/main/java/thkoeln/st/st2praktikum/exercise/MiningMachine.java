package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class MiningMachine {
    @Id
    private UUID id;
    private UUID fieldId;
    private String name;
    private Coordinate coordinate;

    public MiningMachine(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.fieldId = null;
        this.coordinate = new Coordinate(0, 0);
    }

    public MiningMachine(UUID id, Coordinate coordinate, UUID fieldId, String name) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.fieldId = fieldId;
    }

    public void setFieldId(UUID fieldId) {
        this.fieldId = fieldId;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public UUID getFieldId() {
        return fieldId;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public UUID getId() {
        return id;
    }
}
