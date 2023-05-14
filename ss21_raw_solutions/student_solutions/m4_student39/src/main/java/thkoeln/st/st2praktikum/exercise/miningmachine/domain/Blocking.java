package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

public interface Blocking {
    public boolean isBlocked(Vector2D position);
}