package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Dimension {
    private int height;
    private int width;

    public Dimension(int height, int width) {
        this.height = height;
        this.width = width;
    }
    @Override
    public String toString() {
        return "Dimension{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimension dimension = (Dimension) o;
        return height == dimension.height && width == dimension.width;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
