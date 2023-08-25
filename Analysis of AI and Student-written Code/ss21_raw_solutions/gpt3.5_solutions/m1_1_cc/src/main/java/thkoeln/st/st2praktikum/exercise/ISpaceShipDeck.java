package thkoeln.st.st2praktikum.exercise;

public interface ISpaceShipDeck {
    public void addObstacle(ObstacleInterface obstacle);
    public void removeObstacle(ObstacleInterface obstacle);
    public boolean isObstacle(int x, int y);
    public boolean isBoundary(int x, int y);
    public boolean isValidCoordinate(int x, int y);
}
