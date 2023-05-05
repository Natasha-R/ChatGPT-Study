package thkoeln.st.st2praktikum.exercise;

public class CleaningDevice {
    private Point currentPosition;
    private String lastDirection;

    public CleaningDevice(Point initialPosition) {
        this.currentPosition = initialPosition;
        this.lastDirection = "";
    }

    public void moveByOneIn(String direction)
    {
        switch (direction)
        {
            case "ea":
                this.currentPosition.addToX(1);
                break;
            case "we":
                this.currentPosition.addToX(-1);
                break;
            case "no":
                this.currentPosition.addToY(1);
                break;
            case "so":
                this.currentPosition.addToY(-1);
                break;
        }

        this.lastDirection = direction;
    }

    public Point getCurrentPosition()
    {
        return currentPosition;
    }
}
