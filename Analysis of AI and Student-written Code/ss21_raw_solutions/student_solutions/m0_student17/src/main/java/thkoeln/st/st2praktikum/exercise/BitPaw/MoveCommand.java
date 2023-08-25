package thkoeln.st.st2praktikum.exercise.BitPaw;

public class MoveCommand
{
    public MoveDirection Direction;
    public int MoveLength;
    public Axis Orientation;
    public boolean IsPositiveDirection;

    public MoveCommand(final MoveDirection direction,  final int moveLength)
    {
        Direction = direction;
        MoveLength = moveLength;
    }

    public MoveCommand(final String command)
    {
        int firstComma = command.indexOf(',');
        int lengh = command.length();
        boolean validLength = lengh >= 6;
        boolean leftBracket;
        boolean rightBacket;
        String tempString;

        if(!validLength)
        {
            Direction = MoveDirection.None;
            return;
        }

        leftBracket = '[' == command.charAt(0);
        rightBacket = ']' == command.charAt(lengh-1);

        tempString = command.substring(1,firstComma);

        switch (tempString)
        {
            case "no": // (north),
                Direction = MoveDirection.Up;
                Orientation = Axis.Vertical;
                IsPositiveDirection = true;
                break;

            case "ea": // (east),
                Direction = MoveDirection.Right;
                Orientation = Axis.Horizontal;
                IsPositiveDirection = true;
                break;

            case "so": // (south),
                Direction = MoveDirection.Down;
                Orientation = Axis.Vertical;
                IsPositiveDirection = false;
                break;

            case "we": // (west).
                Direction = MoveDirection.Left;
                Orientation = Axis.Horizontal;
                IsPositiveDirection = false;
                break;
        }

        // Parse movement length
        tempString =  command.substring(firstComma+1, lengh-1);

        MoveLength = Integer.parseInt(tempString);
    }

    public Point ConvertToPoint()
    {
        Point point = new Point();

        switch (Direction)
        {
            case Up:
                point.Set(0, MoveLength);
                break;

            case Down:
                point.Set(0, -MoveLength);
                break;

            case Left:
                point.Set(-MoveLength, 0);
                break;

            case Right:
                point.Set(MoveLength, 0);
                break;
        }

        return point;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveCommand that = (MoveCommand) o;

        return equals(that);
    }

    public boolean equals(MoveCommand moveCommand)
    {
        return (MoveLength == moveCommand.MoveLength) && (Direction == moveCommand.Direction);
    }

    @Override
    public String toString()
    {
        String directionTag;

        switch (Direction)
        {
            case Up:
                directionTag = "no";
                break;

            case Down:
                directionTag = "so";
                break;

            case Left:
                directionTag = "we";
                break;

            case Right:
                directionTag = "ea";
                break;

            default:
                directionTag = "ERROR";
                break;
        }

        return "[" +directionTag + "," + MoveLength + "]";
    }
}
