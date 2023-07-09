package thkoeln.st.st2praktikum.map;

import thkoeln.st.st2praktikum.linearAlgebra.BoundedStraight;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

public class BoundedObstacle extends BoundedStraight implements Obstacle {

    public BoundedObstacle(Vector directionVector, Vector offsetVector,
                           double beginLambda, double endLambda) {
        super(directionVector, offsetVector, beginLambda,
                endLambda);
    }

    public BoundedObstacle(Vector startPoint, Vector endPoint) {
        super(startPoint, endPoint);
    }
}
