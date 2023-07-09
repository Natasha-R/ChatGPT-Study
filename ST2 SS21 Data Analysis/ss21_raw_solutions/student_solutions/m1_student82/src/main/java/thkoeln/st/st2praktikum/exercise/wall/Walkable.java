package thkoeln.st.st2praktikum.exercise.wall;

import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;

import java.util.ArrayList;

public interface Walkable {

   Wall getWall();
   ArrayList<Coordinate> getWallCoordinates();
   static boolean containsMaybeWall(Coordinate maybeWallStartCoordinate, Coordinate maybeWallEndCoordinate, ArrayList<Wall> wallList) {
      boolean result = false;
      for (Wall wall: wallList) {
         result = wall.containsMaybeWall(maybeWallStartCoordinate, maybeWallEndCoordinate);
         if(result){
            return result;
         }
      }
      return false;
   }
}
