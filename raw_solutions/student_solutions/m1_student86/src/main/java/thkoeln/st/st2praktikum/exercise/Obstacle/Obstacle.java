package thkoeln.st.st2praktikum.exercise.Obstacle;

import thkoeln.st.st2praktikum.exercise.Space.Space;

public interface Obstacle {
    void buildUp (Space destinationSpace);
    void tearDown (Space sourceSpace);
}