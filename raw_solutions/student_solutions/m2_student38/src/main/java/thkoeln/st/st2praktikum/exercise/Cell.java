package thkoeln.st.st2praktikum.exercise;

class Cell{
    private boolean wallNorth;
    private boolean wallEast;
    private boolean wallSouth;
    private boolean wallWest;
    private boolean busy;

    public Cell(boolean wallNorth, boolean wallEast, boolean wallSouth, boolean wallWest){
        this.setWallNorth(wallNorth);
        this.setWallEast(wallEast);
        this.setWallSouth(wallSouth);
        this.setWallWest(wallWest);
        this.setBusy(false);
    }

    public boolean isWallNorth() {
        return wallNorth;
    }

    public void setWallNorth(boolean wallNorth) {
        this.wallNorth = wallNorth;
    }

    public boolean isWallEast() {
        return wallEast;
    }

    public void setWallEast(boolean wallEast) {
        this.wallEast = wallEast;
    }

    public boolean isWallSouth() {
        return wallSouth;
    }

    public void setWallSouth(boolean wallSouth) {
        this.wallSouth = wallSouth;
    }

    public boolean isWallWest() {
        return wallWest;
    }

    public void setWallWest(boolean wallWest) {
        this.wallWest = wallWest;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}