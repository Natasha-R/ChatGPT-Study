package thkoeln.st.st2praktikum.exercise;

public class Rectangle {
    private Coordinate coordinate;
    private Dimension dimension;

    public Rectangle(Coordinate coordinate, Dimension dimension) {
        this.coordinate = coordinate;
        this.dimension = dimension;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Dimension getDimension() {
        return dimension;
    }



    public boolean isWithinWidth(Rectangle other){
        if (this.coordinate.getX() + this.dimension.getWidth() <= other.coordinate.getX()
                || this.coordinate.getX() >= other.coordinate.getX() + other.dimension.getWidth()) {
            return false;
        }
        return true;
    }

    public boolean isWithinHeight(Rectangle other){
        if (this.coordinate.getY() + this.dimension.getHeight() <= other.coordinate.getY()
                || this.coordinate.getY() >= other.coordinate.getY() + other.dimension.getHeight()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "coordinate=" + coordinate +
                ", dimension=" + dimension +
                '}';
    }


    public boolean isRightAlignedWith(Rectangle other){
        return (this.coordinate.getX() + this.dimension.getWidth() == other.coordinate.getX());
    }

    public boolean isTopAlignedWith(Rectangle other){
        return (this.coordinate.getY() + this.dimension.getHeight() == other.coordinate.getY());
    }

    public boolean isLeftAlignedWith(Rectangle other){
        return other.isRightAlignedWith(this);
    }

    public boolean isBottomAlignedWith(Rectangle other){
        return other.isTopAlignedWith(this);
    }

    public boolean isOverlapping(Rectangle other) {
        return isWithinWidth(other) && isWithinHeight(other);
    }

    static Rectangle fromCoordinates(Coordinate start, Coordinate end){
        Rectangle newRectangle = new Rectangle(
                start,
                new Dimension(
                        end.getY() - start.getY(),
                        end.getX() - start.getX()
                )
        );
        return newRectangle;
    }
}
