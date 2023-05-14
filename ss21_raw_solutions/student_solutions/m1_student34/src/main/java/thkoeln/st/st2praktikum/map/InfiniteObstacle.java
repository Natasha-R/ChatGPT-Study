package thkoeln.st.st2praktikum.map;

import thkoeln.st.st2praktikum.linearAlgebra.Straight;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

public class InfiniteObstacle extends Straight implements Obstacle {

    public InfiniteObstacle(Vector directionVector, Vector offsetVector) {
        super(directionVector, offsetVector);
    }
}
