package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class WallTranslator implements Walkable, UUidable{
    private UUID spaceShipId;
    private UUID wallId = UUID.randomUUID();
    private ArrayList<Coordinate> wallCoordinates = new ArrayList<>();

    public WallTranslator(UUID spaceShipId, Wall wall){
        this.spaceShipId = spaceShipId;
        createWall(wall.getStart(), wall.getEnd());
    }

    @Override
    public UUID getID() {
        return this.wallId;
    }

    @Override
    public WallTranslator getWallTranslator() {
        return this;
    }

    public boolean containsMaybeWall(Coordinate maybeWallStartCoordinate, Coordinate maybeWallEndCoordinate) {
        for (Coordinate coordinate: this.wallCoordinates) {
            if (coordinate.getCoordinate().equals( maybeWallStartCoordinate) ) {
                int index = this.wallCoordinates.indexOf(coordinate);
                System.out.println(index);
                if(index + 1 < this.wallCoordinates.size()){

                    if (this.wallCoordinates.get(index + 1) != null) {

                        if (this.wallCoordinates.get(index + 1).equals(maybeWallEndCoordinate)) {

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


    public void createWall(Coordinate wallPos1, Coordinate wallPos2) {


        if (wallPos1.getX() == wallPos2.getX()) {
            for (int i = wallPos1.getY(); i < wallPos2.getY() + 1; i++) {
                this.wallCoordinates.add(new Coordinate(wallPos1.getX(), i));
            }
        } else if (wallPos1.getY() == wallPos2.getY()) {
            for (int i = wallPos1.getX(); i < wallPos2.getX() + 1; i++) {
                this.wallCoordinates.add(new Coordinate(i, wallPos1.getY()));
            }
        }

    }
}
