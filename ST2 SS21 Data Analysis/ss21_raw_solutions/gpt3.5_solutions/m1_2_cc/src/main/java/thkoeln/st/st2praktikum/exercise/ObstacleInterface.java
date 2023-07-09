package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ObstacleInterface {
    UUID getId();
    int getStartX();
    int getStartY();
    int getEndX();
    int getEndY();
}

