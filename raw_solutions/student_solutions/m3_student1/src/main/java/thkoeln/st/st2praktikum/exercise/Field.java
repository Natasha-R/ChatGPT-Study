package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Entity
public class Field {
    @Id
    private UUID id = UUID.randomUUID();
    private Integer height;
    private Integer width;
    @ElementCollection(targetClass = Barrier.class, fetch = FetchType.EAGER)
    private List<Barrier> barriers = new ArrayList<>();

    public Field() { }

    public void addBarrier(String barrierString) {
        barriers.add(new Barrier(barrierString));
    }
    public void addBarrier(Barrier newBarrier) {
        barriers.add(newBarrier);
    }
    public Boolean isReachable(Point currentPoint, Point nextPoint) {
        Predicate<Barrier> p = barrier -> barrier.isBlocking(currentPoint, nextPoint);
        return barriers.stream().noneMatch(p);
    }

    public UUID getId() {
        return this.id;
    }

    public Field(Integer width, Integer height) {
        this.width = width;
        this.height = height;

        //adding the borders of the room
        barriers.add(new Barrier(new Point(0, 0), new Point(0, height+1)));
        barriers.add(new Barrier(new Point(0, 0), new Point(width+1, 0)));
        barriers.add(new Barrier(new Point(width+1, 0), new Point(width+1, height+1)));
        barriers.add(new Barrier(new Point(0, height+1), new Point(width+1, height+1)));
    }
}
