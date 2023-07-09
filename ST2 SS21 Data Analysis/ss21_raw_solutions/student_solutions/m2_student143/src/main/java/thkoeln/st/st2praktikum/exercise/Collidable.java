package thkoeln.st.st2praktikum.exercise;

public interface Collidable {
    Rectangle getBounds();
    default boolean isCollidingWith(Collidable collidable, Direction direction){
        Rectangle thisRectangle = getBounds();
        Rectangle otherRectangle = collidable.getBounds();
        switch (direction){
            case NONE:
                return thisRectangle.isOverlapping(otherRectangle);
            case NORTH:
                return  thisRectangle.isTopAlignedWith(otherRectangle) &&
                        thisRectangle.isWithinWidth(otherRectangle);
            case SOUTH:
                return  thisRectangle.isBottomAlignedWith(otherRectangle) &&
                        thisRectangle.isWithinWidth(otherRectangle);
            case EAST:
                return  thisRectangle.isRightAlignedWith(otherRectangle) &&
                        thisRectangle.isWithinHeight(otherRectangle);
            case WEST:
                return thisRectangle.isLeftAlignedWith(otherRectangle) &&
                        thisRectangle.isWithinHeight(otherRectangle);
        }
        return false;
    }
}