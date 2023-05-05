package thkoeln.st.st2praktikum.exercise;


import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

public interface Blocking {
    //Check if the coordinates are blocked
    Boolean isBlockingCoordinate(Vector2D coordinates);
}
