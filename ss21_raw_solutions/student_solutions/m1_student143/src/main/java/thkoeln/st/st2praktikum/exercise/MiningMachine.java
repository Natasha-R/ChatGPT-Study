package thkoeln.st.st2praktikum.exercise;

public class MiningMachine extends AbstractEntity
        implements Placable, Steppable, Collidable {

    private String name;
    private Rectangle bounds;

    public MiningMachine(String name, Coordinate leftCorner) {
        this.name = name;
        this.bounds = new Rectangle(
                leftCorner,
                new Dimension(1,1)
        );
    }

    @Override
    public String toString() { return this.bounds.getCoordinate().toString(); }

    @Override
    public Coordinate nextCoordinate(Direction direction) {
        Coordinate coordinate = new Coordinate(bounds.getCoordinate());
        switch (direction){
            case NORTH: coordinate.setY(coordinate.getY() + 1); break;
            case SOUTH: coordinate.setY(coordinate.getY() - 1); break;
            case EAST:  coordinate.setX(coordinate.getX() + 1); break;
            case WEST:  coordinate.setX(coordinate.getX() - 1); break;
        }
        return coordinate;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.bounds.setCoordinate(coordinate);
    }

    @Override
    public Coordinate getCoordinate() {
        return this.bounds.getCoordinate();
    }

    @Override
    public Rectangle getBounds() { return bounds; }
};