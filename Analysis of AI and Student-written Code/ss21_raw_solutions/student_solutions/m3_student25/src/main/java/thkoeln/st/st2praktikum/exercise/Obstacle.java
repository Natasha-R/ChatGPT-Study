package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
public class Obstacle {
    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;
    @Id
    private final UUID obstacleID = UUID.randomUUID();


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
        if (start.getX() > end.getX()) {
            this.start = pos2;
            this.end = pos1;
        }
        if (start.getY() > end.getY()) {
            this.start = pos2;
            this.end = pos1;
        }
        if (!this.start.getX().equals(this.end.getX())) {
            if (!this.start.getY().equals(this.end.getY())) throw new RuntimeException("blbla");
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obstacle obstacle = (Obstacle) o;
        return Objects.equals(start, obstacle.start) && Objects.equals(end, obstacle.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, obstacleID);
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {

        int countLeft = 0;
        int countRight = 0;
        for (int i = 0; i < obstacleString.length(); i++) {
            if (obstacleString.charAt(i) == '(') countLeft++;
            if (obstacleString.charAt(i) == ')') countRight++;
        }
        if (countLeft < 2 || countRight < 2) throw new RuntimeException("obstacleString faulty");
        obstacleString = obstacleString.replace("(", "");
        obstacleString = obstacleString.replace(")", "");
        String[] parts = obstacleString.split("-");
        start = new Coordinate(parts[0]);
        end = new Coordinate(parts[1]);
    }

    protected Obstacle() {
    }

}
