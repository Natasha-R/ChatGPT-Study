package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {

    private CleaningDevice cleaningDevice;

    private Boundary[] boundaries;

    public Exercise0() {
        this.cleaningDevice = new CleaningDevice(
                new Point(4, 0)
        );

        this.boundaries = new Boundary[8];


        // Linke Flanke
        this.boundaries[0] = new Boundary(
                new Point(1, 0),
                new Point(1, 6)
        );
        this.boundaries[1] = new Boundary(
                new Point(1, 6),
                new Point(4, 6)
        );

        // Rechts Flanke
        this.boundaries[2] = new Boundary(
                new Point(7, 1),
                new Point(7, 6)
        );
        this.boundaries[3] = new Boundary(
                new Point(5, 6),
                new Point(7, 6)
        );

        // Raum
        this.boundaries[4] = new Boundary(
                new Point(0, 0),
                new Point(12, 0)
        );
        this.boundaries[5] = new Boundary(
                new Point(0, 8),
                new Point(12, 8)
        );
        this.boundaries[6] = new Boundary(
                new Point(0, 0),
                new Point(0, 8)
        );
        this.boundaries[7] = new Boundary(
                new Point(12, 0),
                new Point(12, 8)
        );
    }

    @Override
    public String walk(String walkCommandString) {

        System.out.println(walkCommandString);

        this.decodeWalkCommandString(walkCommandString);

        return String.format("(%d,%d)", this.cleaningDevice.getCurrentPosition().getX(), this.cleaningDevice.getCurrentPosition().getY());
    }

    private void decodeWalkCommandString(String walkCommandString)
    {
        String walkCommandStringRegex = "\\[(no|ea|so|we),([0-9]+)\\]";
        Pattern pattern = Pattern.compile(walkCommandStringRegex);

        Matcher matcher = pattern.matcher(walkCommandString);

        if (!matcher.matches())
        {
            return;
        }

        String direction = matcher.group(1);
        int steps = Integer.parseInt(matcher.group(2));

        for (int stepper = 0; stepper < steps; stepper++)
        {
            System.out.println(String.format("(%d,%d)", this.cleaningDevice.getCurrentPosition().getX(), this.cleaningDevice.getCurrentPosition().getY()));
            if(this.willDeviceCollideInDirection(direction))
            {
                break;
            }

            this.cleaningDevice.moveByOneIn(direction);
        }
    }

    private boolean willDeviceCollideInDirection(String direction)
    {
        Point currentDevicePos = this.cleaningDevice.getCurrentPosition();

        for (Boundary boundary: this.boundaries)
        {
            Point collidingPoint = new Point(currentDevicePos.getX(), currentDevicePos.getY());

            switch (direction)
            {
                case "ea": // rechts
                    if(!boundary.isVertical())
                    {
                        continue;
                    }
                    collidingPoint.setX(collidingPoint.getX() + 1);
                    break;
                case "we": // links
                    if(!boundary.isVertical())
                    {
                        continue;
                    }
                    break;
                case "no":
                    if(boundary.isVertical())
                    {
                        continue;
                    }
                    collidingPoint.setY(collidingPoint.getY() + 1);
                    break;
                case "so":
                    if(boundary.isVertical())
                    {
                        continue;
                    }
                    break;
            }

            if(boundary.isPointColliding(collidingPoint))
            {
                System.out.println(String.format("Colliding Boundary %d %d", boundary.getStartPoint().getX(), boundary.getStartPoint().getY()));
                return true;
            }
        }

        return false;
    }
}
