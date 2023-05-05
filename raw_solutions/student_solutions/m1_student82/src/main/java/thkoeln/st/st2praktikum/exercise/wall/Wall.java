package thkoeln.st.st2praktikum.exercise.wall;

import thkoeln.st.st2praktikum.exercise.CoordinateService;
import thkoeln.st.st2praktikum.exercise.UUidable;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.wall.Walkable;

import java.util.ArrayList;
import java.util.UUID;

public class Wall implements Walkable, UUidable {

    private UUID spaceShipId;
    private UUID wallId = UUID.randomUUID();
    private ArrayList<Coordinate> wallCoordinates = new ArrayList<>();

    public Wall(UUID spaceShipId, String wallString){
        this.spaceShipId = spaceShipId;
        createWall(wallString);
    }

    @Override
    public UUID getID() {
        return this.wallId;
    }

    @Override
    public Wall getWall() {
        return this;
    }

    public boolean containsMaybeWall(Coordinate maybeWallStartCoordinate, Coordinate maybeWallEndCoordinate) {
        for (Coordinate coordinate: this.wallCoordinates) {
            if (coordinate.getCoordinate().toString().equals( maybeWallStartCoordinate.toString()) ) {
                int index = this.wallCoordinates.indexOf(coordinate);
                System.out.println(index);
                if(index + 1 < this.wallCoordinates.size()){

                    if (this.wallCoordinates.get(index + 1) != null) {

                        if (this.wallCoordinates.get(index + 1).toString().equals(maybeWallEndCoordinate.toString())) {

                            return true;
                    }
                }

                }
            }
            
        }
        return false;
    }

    @Override
    public ArrayList<Coordinate> getWallCoordinates() {
        return this.wallCoordinates;
    }


    public void createWall(String wallString) {
        String[] wallItem = wallString.split("-");
        Coordinate startCoordinate = CoordinateService.stringToCoordinate(wallItem[0]);
        Coordinate endCoordinate = CoordinateService.stringToCoordinate(wallItem[1]);

        if (startCoordinate.getXAxes() == endCoordinate.getXAxes()) {
            for (int i = startCoordinate.getYAxes(); i < endCoordinate.getYAxes() + 1; i++) {
                this.wallCoordinates.add(new Coordinate(startCoordinate.getXAxes(), i));
            }
        } else if (startCoordinate.getYAxes() == endCoordinate.getYAxes()) {
            for (int i = startCoordinate.getXAxes(); i < endCoordinate.getXAxes() + 1; i++) {
                this.wallCoordinates.add(new Coordinate(i, startCoordinate.getYAxes()));
            }
        }

    }

}
