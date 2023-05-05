package thkoeln.st.st2praktikum.exercise.room.application;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Room {
    @Id
    @Getter
    private UUID id;
    private int width;
    private int height;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Barrier> barriers = new ArrayList<>();

    public Room(int width, int height) {
        this.id = UUID.randomUUID();
        this.width = width;
        this.height = height;

        createRoomBorders(width, height);
    }

    private void createRoomBorders(int width, int height) {
        Point topLeft = new Point(0, height);
        Point topRight = new Point(width, height);
        Point bottomLeft = new Point(0, 0);
        Point bottomRight = new Point(width, 0);

        this.addBarrier(new Barrier(bottomLeft, topLeft));
        this.addBarrier(new Barrier(topLeft, topRight));
        this.addBarrier(new Barrier(bottomRight, topRight));
        this.addBarrier(new Barrier(bottomLeft, bottomRight));
    }

    public void addBarrier(Barrier barrier) {
        if(isBarrierOutOfBounds(barrier)) {
            throw new RuntimeException("Barrier out of bounds");
        }
        this.barriers.add(barrier);
    }

    private Boolean isBarrierOutOfBounds(Barrier barrier) {
        boolean isLeftOutOfBounds = barrier.getStart().getX() < 0;
        boolean isBottomOutOfBounds = barrier.getStart().getY() < 0;
        boolean isRightOutOfBounds = barrier.getEnd().getX() > this.width;
        boolean isTopOutOfBounds = barrier.getEnd().getY() > this.height;

        return isLeftOutOfBounds || isBottomOutOfBounds || isRightOutOfBounds || isTopOutOfBounds;
    }

    public Barrier[] getHorizontalBarriers() {
        return this.barriers.stream().filter(barrier -> barrier.isHorizontal()).toArray(Barrier[]::new);
    }

    public Barrier[] getVerticalBarriers() {
        return this.barriers.stream().filter(barrier -> barrier.isVertical()).toArray(Barrier[]::new);
    }

    @Nullable
    public Point coordinateOfBarrierBetween(Point start, Point end) {
        boolean isVerticalMovement = start.getX() == end.getX();

        if(isVerticalMovement) {
            return getCoordinateOfBarrierDuringVerticalMovement(start, end);
        } else {
            return getCoordinateOfBarrierDuringHorizontalMovement(start, end);
        }
    }

    @Nullable
    private Point getCoordinateOfBarrierDuringVerticalMovement(Point start, Point end) {
        if (start.getY() > end.getY()) {
            Point tmp = start;
            start = end;
            end = tmp;
        }

        for(Barrier barrier: this.getHorizontalBarriers()) {
            boolean isBarrierAboveStart = barrier.getStart().getY() > start.getY();
            boolean isBarrierUnderEnd = barrier.getStart().getY() <= end.getY();
            boolean isBarrierBetweenStartAndEnd = isBarrierAboveStart && isBarrierUnderEnd;
            boolean isBarrierInDirectPath = barrier.getStart().getX() <= start.getX() && barrier.getEnd().getX() > start.getX();

            if(isBarrierBetweenStartAndEnd && isBarrierInDirectPath) {
                System.out.println("Something Between " + start.toString() + " and " + end.toString());
                System.out.println("Wall coordinates: " + barrier.getStart().toString() + ", " + barrier.getEnd().toString());
                return new Point(start.getX(), barrier.getStart().getY());
            }
        }

        return null;
    }

    @Nullable
    private Point getCoordinateOfBarrierDuringHorizontalMovement(Point start, Point end) {
        if (start.getX() > end.getX()) {
            Point tmp = start;
            start = end;
            end = tmp;
        }

        for(Barrier barrier: this.getVerticalBarriers()) {
            boolean isBarrierRightOfStart = barrier.getStart().getX() > start.getX();
            boolean isBarrierLeftOfEnd = barrier.getStart().getX() <= end.getX();
            boolean isBarrierBetweenStartAndEnd = isBarrierRightOfStart && isBarrierLeftOfEnd;
            boolean isBarrierInDirectPath = (barrier.getStart().getY() <= start.getY() && barrier.getEnd().getY() > start.getY());

            if(isBarrierBetweenStartAndEnd && isBarrierInDirectPath) {
                System.out.println("Something Between " + start.toString() + " and " + end.toString());
                System.out.println("Wall coordinates: " + barrier.getStart().toString() + ", " + barrier.getEnd().toString());
                return new Point(barrier.getStart().getX(), start.getY());
            }
        }

        return null;
    }
}