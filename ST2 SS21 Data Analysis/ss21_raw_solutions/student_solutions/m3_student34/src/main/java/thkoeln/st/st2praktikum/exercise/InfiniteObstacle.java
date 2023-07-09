package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfiniteObstacle extends Straight implements IObstacle {

    public InfiniteObstacle(Vector directionVector, Vector offsetVector) {
        super(directionVector, offsetVector);
    }
}
