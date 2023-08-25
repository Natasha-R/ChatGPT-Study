package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Moveable {

    private static final boolean enableLogging = true;

    // Configure Defaults
    private final Coordinate startPosition = new Coordinate(0, 2);
    private final int gridHeight = 9;
    private final int gridWidth = 12;
    private final Border[] borders = {
            new Border(new Coordinate(3, 0), new Coordinate(3, 3), "y"),
            new Border(new Coordinate(5, 0), new Coordinate(5, 4), "y"),
            new Border(new Coordinate(7, 5), new Coordinate(7, 9), "y"),
            new Border(new Coordinate(4, 5), new Coordinate(7, 5), "x")
    };

    // Robot Position
    private final Coordinate position;

    public Exercise0() {
        this.position = startPosition;
    }

    @Override
    public String moveTo(String moveCommandString) {
        Border relevantBorder;
        String direction = "";
        int amount = 0;

        // use Regex to parse command String to get direction (e.g. 'ea') and movement amount
        Pattern pattern = Pattern.compile("\\[(.*)(?:,)(.*)]");
        Matcher matcher = pattern.matcher(moveCommandString);
        while (matcher.find()) {
            direction = matcher.group(1);
            amount = Integer.parseInt(matcher.group(2));
        }

        if (enableLogging) {
            System.out.println("Start Position: (" + this.position.getX() + "," + this.position.getY() + ")");
            System.out.println("move Command: " + moveCommandString);
        }

        switch (direction) {
            case "no": {
                relevantBorder = this.getFirstRelevantBorder("y");
                this.position.incrementY(amount);
                this.clampPositionToBorder(relevantBorder, "+y");
            }
            break;
            case "ea": {
                relevantBorder = this.getFirstRelevantBorder("x");
                this.position.incrementX(amount);
                this.clampPositionToBorder(relevantBorder, "+x");
            }
            break;
            case "so": {
                relevantBorder = this.getFirstRelevantBorder("y");
                this.position.decrementY(amount);
                this.clampPositionToBorder(relevantBorder, "-y");
            }
            break;
            case "we": {
                relevantBorder = this.getFirstRelevantBorder("x");
                this.position.decrementX(amount);
                this.clampPositionToBorder(relevantBorder, "-x");
            }
            break;
            default:
                throw new InvalidParameterException();
        }

        this.clampPositionToGrid();

        if (enableLogging) {
            System.out.println("End Position: (" + this.position.getX() + "," + this.position.getY() + ")");
            System.out.println("--------");
        }

        return "(" + this.position.getX() + "," + this.position.getY() + ")";
    }

    private void clampPositionToGrid() {
        if (this.position.getX() > this.gridWidth - 1) this.position.setX(this.gridWidth - 1);
        if (this.position.getY() > this.gridHeight - 1) this.position.setY(this.gridHeight - 1);
        if (this.position.getX() < 0) this.position.setX(0);
        if (this.position.getY() < 0) this.position.setY(0);
    }

    /**
     * Clamp the position to respect impenetrable borders
     *
     * @param border       the border that has to be respected
     * @param movementAxis the direction of the movement
     */
    private void clampPositionToBorder(Border border, String movementAxis) {
        if (enableLogging) {
            System.out.println("Position before Clamp: " + this.position);
            System.out.println("Movement: " + movementAxis);
            System.out.println("Border: " + border);
        }

        if (border != null) {
            switch (movementAxis) {
                case "+x": {
                    if (border.start.getX() <= this.position.getX()) this.position.setX(border.start.getX() - 1);
                    break;
                }
                case "+y": {
                    if (border.start.getY() <= this.position.getY()) this.position.setY(border.start.getY() - 1);
                    break;
                }
                case "-x": {
                    if (border.start.getX() >= this.position.getX()) this.position.setX(border.start.getX());
                    break;
                }
                case "-y": {
                    if (border.start.getY() <= this.position.getY()) this.position.setY(border.start.getY());
                    break;
                }
                default:
                    throw new InvalidParameterException();
            }
        }

        if (enableLogging) {
            System.out.println("Position after Border Clamp: " + this.position);
        }
    }

    /**
     * @return get the nearest Border that is relevant to the next movement
     */
    private Border getFirstRelevantBorder(String axis) {
        Border[] relevantBorders = getRelevantBorders(axis);
        return relevantBorders.length > 0 ? relevantBorders[0] : null;
    }

    /**
     * @return Array with all borders that lie on the given axis and that are reachable by a straight move along the axis
     */
    private Border[] getRelevantBorders(String movementAxis) {
        ArrayList<Border> relevantBorders = new ArrayList<>();

        switch (movementAxis) {
            case "x": {
                Border[] bordersByAxis = this.getRelevantBordersByAxis("y");
                for (Border border : bordersByAxis) {
                    if (border.start.getY() <= this.position.getY() && border.end.getY() >= this.position.getY() + 1)
                        relevantBorders.add(border);
                }
                break;
            }
            case "y": {
                Border[] bordersByAxis = this.getRelevantBordersByAxis("x");
                for (Border border : bordersByAxis) {
                    if (border.start.getX() <= this.position.getX() && border.end.getX() >= this.position.getX() + 1)
                        relevantBorders.add(border);
                }
                break;
            }
            default:
                throw new InvalidParameterException();
        }

        return relevantBorders.toArray(size -> new Border[size]);
    }

    /**
     * @param axis axis in the grid
     * @return Array with all Borders that lie on the given axis
     */
    private Border[] getRelevantBordersByAxis(String axis) {
        Border[] relevantBorders = Arrays.stream(this.borders).filter(n -> n.axis.equals(axis)).toArray(size -> new Border[size]);

        switch (axis) {
            case "x": {
                Arrays.sort(relevantBorders, Comparator.comparingInt(b -> b.start.getY()));
                break;
            }
            case "y": {
                Arrays.sort(relevantBorders, Comparator.comparingInt(b -> b.start.getX()));
                break;
            }
            default:
                throw new InvalidParameterException();
        }

        return relevantBorders;
    }
}
