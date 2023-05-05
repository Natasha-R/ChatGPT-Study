package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
public class Field {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    private Integer height, width;

    @ElementCollection(targetClass = Barrier.class, fetch = FetchType.EAGER)
    private final List<Barrier> barriers = new ArrayList<>();

    public Field(Integer height, Integer width) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("width and height must be > 0!");
        this.height = height;
        this.width = width;
    }

    public UUID getFieldId() {
        return this.id;
    }

    public List<Barrier> getBarriers() {
        return this.barriers;
    }

    protected Field() {
    }
}
