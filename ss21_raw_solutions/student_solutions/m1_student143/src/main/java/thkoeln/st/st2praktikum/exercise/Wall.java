package thkoeln.st.st2praktikum.exercise;

public class Wall extends AbstractEntity
        implements Placable, Collidable {

    private Rectangle bounds;

    public Wall(Rectangle rectangle) {
        this.bounds = rectangle;
    }

    static Wall fromString(String wallString) {
        String [] coordinateStrings = wallString.split("-");
        Coordinate start = Coordinate.fromString(coordinateStrings[0]);
        Coordinate end = Coordinate.fromString(coordinateStrings[1]);

        Wall newWall = new Wall(Rectangle.fromCoordinates(start,end));
        return newWall;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.bounds.setCoordinate(coordinate);
    }

    @Override
    public Coordinate getCoordinate() {
        return bounds.getCoordinate();
    }

    @Override
    public Rectangle getBounds(){return bounds;}

};
