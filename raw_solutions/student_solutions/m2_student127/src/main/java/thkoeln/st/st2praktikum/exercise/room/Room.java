package thkoeln.st.st2praktikum.exercise.room;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Room {
    private final UUID id;
    private final Integer height;
    private final Integer width;
    private final List<Barrier> barriers;

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

    public Room(Integer width, Integer height) {
        this.id = UUID.randomUUID();
        this.width = width;
        this.height = height;
        barriers = new ArrayList<>();

        //adding the borders of the room
        barriers.add(new Barrier(new Point(0, 0), new Point(0, height+1)));
        barriers.add(new Barrier(new Point(0, 0), new Point(width+1, 0)));
        barriers.add(new Barrier(new Point(width+1, 0), new Point(width+1, height+1)));
        barriers.add(new Barrier(new Point(0, height+1), new Point(width+1, height+1)));
    }
}
