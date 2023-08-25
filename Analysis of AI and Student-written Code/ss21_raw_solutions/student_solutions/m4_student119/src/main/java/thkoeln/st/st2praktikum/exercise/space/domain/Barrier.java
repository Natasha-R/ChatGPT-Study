package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Barrier {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="x", column= @Column(name="start_x")),
            @AttributeOverride(name="y", column= @Column(name="start_y"))
    })
    private Point start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="x", column= @Column(name="end_x")),
            @AttributeOverride(name="y", column= @Column(name="end_y"))
    })
    private Point end;

    public Barrier(Point pos1, Point pos2) {
        sortPoints(pos1, pos2);
        ensureNotDiagonal();
    }

    private void ensureNotDiagonal() {
        if(!(this.start.getX().equals(this.end.getX()) || this.start.getY().equals(this.end.getY())))
            throw new IllegalArgumentException("diagonal barriers are not supported!");
    }

    private void sortPoints(Point pointA, Point pointB) {
        if(pointA.getX() <= pointB.getX() && pointA.getY() <= pointB.getY()) {
            this.start = pointA;
            this.end = pointB;
        } else {
            this.start = pointB;
            this.end = pointA;
        }
    }

    private BarrierType getBarrierTypeFromPoints() {
        return this.start.getX().equals(this.end.getX()) ? BarrierType.vertical : BarrierType.horizontal;
    }

    public static Barrier fromString(String barrierString) {
        final Pattern pattern = Pattern.compile("^(\\((\\d+),(\\d+)\\))-(\\((\\d+),(\\d+)\\))$");
        final Matcher matcher = pattern.matcher(barrierString);

        if(matcher.find()) {
            final String start = matcher.group(1);
            final String end = matcher.group(4);

            final Point pointA = new Point(start);
            final Point pointB = new Point(end);

            return new Barrier(pointA, pointB);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public BarrierType getBarrierType() {
        return getBarrierTypeFromPoints();
    }
}
