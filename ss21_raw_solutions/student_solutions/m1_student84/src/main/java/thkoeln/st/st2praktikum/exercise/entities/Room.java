package thkoeln.st.st2praktikum.exercise.entities;

import com.sun.istack.Nullable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Room {
    @Getter
    UUID id;

    List<Barrier> barriers = new ArrayList<>();

    public Room(int width, int height) {
        this.id = UUID.randomUUID();

        Coordinate topLeft = new Coordinate(0, height);
        Coordinate topRight = new Coordinate(width, height);
        Coordinate bottomLeft = new Coordinate(0, 0);
        Coordinate bottomRight = new Coordinate(width, 0);

        this.addBarrier(new Barrier(bottomLeft, topLeft));
        this.addBarrier(new Barrier(topLeft, topRight));
        this.addBarrier(new Barrier(bottomRight, topRight));
        this.addBarrier(new Barrier(bottomLeft, bottomRight));
    }

    public void addBarrier(Barrier barrier) {
        this.barriers.add(barrier);
    }

    public Barrier[] getHorizontalBarriers() {
        return this.barriers.stream().filter(barrier -> barrier.start.y == barrier.end.y).toArray(Barrier[]::new);
    }

    public Barrier[] getVerticalBarriers() {
        return this.barriers.stream().filter(barrier -> barrier.start.x == barrier.end.x).toArray(Barrier[]::new);
    }

    @Nullable
    public Coordinate coordinateOfBarrierBetween(Coordinate start, Coordinate end) {
        if(start.x == end.x) {
            // vertical movement
            if (start.y > end.y) {
                Coordinate tmp = start;
                start = end;
                end = tmp;
            }
            for(Barrier barrier: this.getHorizontalBarriers()) {
                if((barrier.start.y > start.y && barrier.start.y <= end.y)) {
                    // There is a barrier between start and end. Now we have to check if it is in the direct path
                    if((barrier.start.x <= start.x && barrier.end.x > start.x)) {
                        System.out.println("Something Between " + start.toString() + " and " + end.toString());
                        System.out.println("Wall coordinates: " + barrier.start.toString() + ", " + barrier.end.toString());
                        return new Coordinate(start.x, barrier.start.y);
                    }
                }
            }
        } else {
            // horizontal movement
            if (start.x > end.x) {
                Coordinate tmp = start;
                start = end;
                end = tmp;
            }
            for(Barrier barrier: this.getVerticalBarriers()) {
                if((barrier.start.x > start.x && barrier.start.x <= end.x)) {
                    // There is a barrier between start and end. Now we have to check if it is in the direct path
                    if((barrier.start.y <= start.y && barrier.end.y > start.y)) {
                        System.out.println("Something Between " + start.toString() + " and " + end.toString());
                        System.out.println("Wall coordinates: " + barrier.start.toString() + ", " + barrier.end.toString());
                        return new Coordinate(barrier.start.x, start.y);
                    }
                }
            }
        }

        return null;
    }
}
