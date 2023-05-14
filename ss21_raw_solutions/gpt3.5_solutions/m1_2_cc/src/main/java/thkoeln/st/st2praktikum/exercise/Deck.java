package thkoeln.st.st2praktikum.exercise;

import java.util.*;

interface Deck {
    UUID getId();
    int getHeight();
    int getWidth();
    boolean hasObstacle(int x, int y);
    boolean isOutOfBounds(int x, int y);
    boolean isValidMove(int x, int y);
    void addObstacle(int startX, int startY, int endX, int endY);
}
