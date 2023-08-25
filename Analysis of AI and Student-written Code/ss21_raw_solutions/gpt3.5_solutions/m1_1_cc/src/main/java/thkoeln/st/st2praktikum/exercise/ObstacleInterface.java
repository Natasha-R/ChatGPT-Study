package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ObstacleInterface {
    UUID getId();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    boolean contains(int x, int y);
}