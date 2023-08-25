package thkoeln.st.st2praktikum.exercise;

public class Cell {
    private boolean borderNorth;
    private boolean borderEast;
    private boolean borderSouth;
    private boolean borderWest;

    public Cell() {
        this.borderNorth = false;
        this.borderEast = false;
        this.borderSouth = false;
        this.borderWest = false;
    }

    public boolean isBorderNorth() {
        return borderNorth;
    }

    public void setBorderNorth(boolean borderNorth) {
        this.borderNorth = borderNorth;
    }

    public boolean isBorderEast() {
        return borderEast;
    }

    public void setBorderEast(boolean borderEast) {
        this.borderEast = borderEast;
    }

    public boolean isBorderSouth() {
        return borderSouth;
    }

    public void setBorderSouth(boolean borderSouth) {
        this.borderSouth = borderSouth;
    }

    public boolean isBorderWest() {
        return borderWest;
    }

    public void setBorderWest(boolean borderWest) {
        this.borderWest = borderWest;
    }
}
