package thkoeln.st.st2praktikum.exercise;

public interface ObstaclePassable {
    Coordinate getUnaccessedCoordinate(Coordinate actualCoordinate, Coordinate targetCoordinate);
}