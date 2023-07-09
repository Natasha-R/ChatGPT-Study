package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.*;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Barrier {

    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_1")),
            @AttributeOverride(name = "y", column = @Column(name = "y_1"))
    })
    @Embedded
    private Vector2D start;
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_2")),
            @AttributeOverride(name = "y", column = @Column(name = "y_2"))
    })
    @Embedded
    private Vector2D end;


    public Barrier(Vector2D start, Vector2D end) {


        if ((start.getX() != end.getX() && start.getY() != end.getY())
                || (start.getX() == end.getX() && start.getY() == end.getY())
        ) {
            throw new RuntimeException("Barrieren müssen horizontal oder Vertikal verlaufen");
        }

        if (end.getX() < start.getX() || end.getY() < start.getY()) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
        
    }
    
    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {

        if (barrierString == null) {
            throw new UnsupportedOperationException();
        }

        if (!barrierString.matches("\\(\\d,\\d\\)-\\(\\d,\\d\\)")) {
            throw new RuntimeException("Falscher Eingabestring");

        }

        String[] parts = barrierString.split(Pattern.quote("-"));
        Vector2D startcheck =  Vector2D.fromString(parts[0]);
        Vector2D endcheck =  Vector2D.fromString(parts[1]);

        if ((startcheck.getX() != endcheck.getX() && startcheck.getY() != endcheck.getY())
                || (startcheck.getX() == endcheck.getX() && startcheck.getY() == endcheck.getY())
        ) {
            throw new RuntimeException("Barrieren müssen horizontal oder Vertikal verlaufen");
        }


        if (endcheck.getX() < startcheck.getX() || endcheck.getY() < startcheck.getY()) {
            return new Barrier(endcheck,startcheck);

        }

        return new Barrier(startcheck,endcheck);

    }
    
}
