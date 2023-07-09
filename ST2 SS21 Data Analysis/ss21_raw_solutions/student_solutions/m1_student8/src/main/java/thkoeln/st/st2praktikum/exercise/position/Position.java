package thkoeln.st.st2praktikum.exercise.position;

public class Position implements Positionable
{
    private int positionX;

    private int positionY;



    public Position(String coordinate)
    {
        extractPositions(coordinate);
    }


    private void extractPositions(String coordinate)
    {
        coordinate = coordinate.replace("(", "").replace(")", "");
        String splitCoordinate[] = coordinate.split(",");
        this.positionX = Integer.parseInt(splitCoordinate[0]);
        this.positionY = Integer.parseInt(splitCoordinate[1]);
    }

    @Override
    public int getCoordinateX() {
        return positionX;
    }

    @Override
    public int getCoordinateY() {
        return positionY;
    }

    @Override
    public void setCoordinateX(int position)
    {
        this.positionX = position;
    }

    @Override
    public void setCoordinateY(int position)
    {
        this.positionY = position;
    }
}
