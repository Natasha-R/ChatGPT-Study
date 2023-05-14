package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Field {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    private Integer height, width;

    @ElementCollection(targetClass = Barrier.class, fetch = FetchType.EAGER)
    @Setter
    @Getter
    private List<Barrier> barrier = new ArrayList<>();

    public Field(Integer height, Integer width) {
        if (width <= 0) throw new IllegalArgumentException("width must be > 0!");
        if (height <= 0) throw new IllegalArgumentException("height must be > 0!");
        this.height = height;
        this.width = width;
    }

    public ArrayList<Barrier> getBarriers() {
        return (ArrayList<Barrier>) barrier;
    }

    protected Field() {
    }
}