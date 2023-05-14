package thkoeln.st.st2praktikum.exercise;


public class MiningMachine extends AbstractEntity
        implements FieldItem, Placeable, Steppable, Collidable {

    private final Rectangle bounds;

    public MiningMachine(String name, Coordinate leftCorner) {
        this.bounds = new Rectangle(
                leftCorner,
                new Dimension(1,1)
        );
    }

    @Override
    public Coordinate nextCoordinate(Direction direction) {
        Coordinate coordinate = bounds.getCoordinate();
        switch (direction){
            case NORTH: return new Coordinate(coordinate.getX(), coordinate.getY()+1);
            case SOUTH: return new Coordinate(coordinate.getX(), coordinate.getY()-1);
            case EAST:  return new Coordinate(coordinate.getX() + 1 , coordinate.getY());
            case WEST:  return new Coordinate(coordinate.getX() - 1, coordinate.getY());
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
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public String toString() {
        return this.bounds.getCoordinate().toString();
    }
};