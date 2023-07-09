package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Obstacle implements ObstacleInterface {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final UUID id;

    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }
}