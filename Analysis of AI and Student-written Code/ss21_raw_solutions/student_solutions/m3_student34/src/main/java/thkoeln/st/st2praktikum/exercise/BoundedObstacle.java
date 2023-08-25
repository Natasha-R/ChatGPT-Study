package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoundedObstacle extends BoundedStraight implements IObstacle {

    public BoundedObstacle(Vector directionVector, Vector offsetVector,
                           double beginLambda, double endLambda) {
        super(directionVector, offsetVector, beginLambda,
                endLambda);
    }

    public BoundedObstacle(Vector startPoint, Vector endPoint) {
        super(startPoint, endPoint);
    }
}
