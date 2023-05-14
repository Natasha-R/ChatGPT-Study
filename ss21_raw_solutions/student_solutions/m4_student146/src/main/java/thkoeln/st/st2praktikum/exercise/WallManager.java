package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.SingleWall;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WallManager {

    /**
     * With this method you get all walls that are in the positive movement area (north and east)
     * @param field the ID of the current field
     * @param base the coordinate which is the same (for example at north&south the x coordinate won'nt change)
     * @param i the coordinate which is about to change
     * @param value the amount of movement
     * @param orderType if the wall is horizontal or vertical
     * @return a list of the walls
     */
    public List<SingleWall> getPositiveWallList(Field field, int base, int i, int value, OrderType orderType) {
        switch (orderType) {
            case NORTH:
                return field.getHorizontalWalls().stream().filter(singleWall -> {
                    int a = singleWall.getX();
                    return a == base && i < singleWall.getY() && (i + value) >= singleWall.getY();
                }).collect(Collectors.toList());
            case EAST:
                return field.getVerticalWalls().stream().filter(singleWall -> {
                    int a = singleWall.getY();
                    return a == base && i < singleWall.getX() && (i + value) >= singleWall.getX();
                }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * With this method you get all walls that are in the negative movement area (south and west)
     * @param field the ID of the current field
     * @param base the coordinate which is the same (for example at north&south the x coordinate won'nt change)
     * @param i the coordinate which is about to change
     * @param value the amount of movement
     * @param orderType if the wall is horizontal or vertical
     * @return a list of the walls
     */
    public List<SingleWall> getNegativeWallList(Field field, int base, int i, int value, OrderType orderType) {
        switch (orderType) {
            case SOUTH:
                return field.getHorizontalWalls().stream().filter(singleWall -> {
                    int a = singleWall.getX();
                    return a == base && i > singleWall.getY() && (i - value) < singleWall.getY();
                }).collect(Collectors.toList());
            case WEST:
                return field.getVerticalWalls().stream().filter(singleWall -> {
                    int a = singleWall.getY();
                    return a == base && i > singleWall.getX() && (i - value) < singleWall.getX();
                }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * This method calculate the y value for the south movement
     *
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x     the current x coordinate
     * @param y     the current y coordinate
     * @return the new y coordinate
     */
    public int calculateSouth(Field field, int value, int x, int y) {
        List<SingleWall> list = getNegativeWallList(field, x, y, value, OrderType.SOUTH);
        if (list.isEmpty())
            y -= value;
        else
            y = list.stream().mapToInt(SingleWall::getY).max().getAsInt();
        return Math.max(y, 0);
    }

    /**
     * This method calculate the y value for the north movement
     *
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x     the current x coordinate
     * @param y     the current y coordinate
     * @return the new y coordinate
     */
    public int calculateNorth(Field field, int value, int x, int y) {
        List<SingleWall> list = getPositiveWallList(field, x, y, value, OrderType.NORTH);
        if (list.isEmpty())
            y += value;
        else {
            y = list.stream().mapToInt(SingleWall::getY).min().getAsInt() - 1;
        }
        int maxHeight = field.getStart();
        return Math.min(y, maxHeight);
    }

    /**
     * This method calculate the x value for the west movement
     *
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x     the current x coordinate
     * @param y     the current y coordinate
     * @return the new x coordinate
     */
    public int calculateWest(Field field, int value, int x, int y) {
        List<SingleWall> list = getNegativeWallList(field, y, x, value, OrderType.WEST);
        if (list.isEmpty())
            x -= value;
        else
            x = list.stream().mapToInt(SingleWall::getX).max().getAsInt();
        return Math.max(x, 0);
    }

    /**
     * This method calculate the x value for the east movement
     *
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x     the current x coordinate
     * @param y     the current y coordinate
     * @return the new x coordinate
     */
    public int calculateEast(Field field, int value, int x, int y) {
        List<SingleWall> list = getPositiveWallList(field, y, x, value, OrderType.EAST);
        if (list.isEmpty())
            x += value;
        else
            x = list.stream().mapToInt(SingleWall::getX).min().getAsInt() - 1;
        int maxWidth = field.getEnd();
        return Math.min(x, maxWidth);
    }
}
