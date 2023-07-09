package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public interface Walkable {
    WallTranslator getWallTranslator();
    ArrayList<Coordinate> getWallCoordinates();
    static boolean containsMaybeWall(Coordinate maybeWallStartCoordinate, Coordinate maybeWallEndCoordinate, ArrayList<WallTranslator> wallList) {
        boolean result = false;
        for (WallTranslator wall: wallList) {
            result = wall.containsMaybeWall(maybeWallStartCoordinate, maybeWallEndCoordinate);
            if(result){
                return result;
            }
        }
        return false;
    }
}
