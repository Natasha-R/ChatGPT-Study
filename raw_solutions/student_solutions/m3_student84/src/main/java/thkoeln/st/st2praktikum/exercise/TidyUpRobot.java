package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class TidyUpRobot {
    @Id
    @Getter
    private UUID id;
    private String name;
    @Getter
    @Setter
    @Embedded
    private Point currentPosition;
    @Getter
    @Setter
    private UUID roomId;

    public TidyUpRobot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Point getPoint() {
        return this.currentPosition;
    }
}
