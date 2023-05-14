package thkoeln.st.st2praktikum.exercise;

public class Cell {

    private boolean borderNorth;
    private boolean borderEast;
    private boolean borderSouth;
    private boolean borderWest;

    public Cell(){
        borderNorth = false;
        borderEast = false;
        borderSouth = false;
        borderWest = false;
    }

    public boolean isBorderNorth() {
        return borderNorth;
    }

    public boolean isBorderEast() {
        return borderEast;
    }

    public boolean isBorderSouth() {
        return borderSouth;
    }

    public boolean isBorderWest() {
        return borderWest;
    }

    public void setBorderNorth(boolean borderNorth) {
        this.borderNorth = borderNorth;
    }

    public void setBorderEast(boolean borderEast) {
        this.borderEast = borderEast;
    }

    public void setBorderSouth(boolean borderSouth) {
        this.borderSouth = borderSouth;
    }

    public void setBorderWest(boolean borderWest) {
        this.borderWest = borderWest;
    }
}
