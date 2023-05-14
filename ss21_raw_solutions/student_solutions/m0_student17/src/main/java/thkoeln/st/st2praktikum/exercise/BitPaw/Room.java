package thkoeln.st.st2praktikum.exercise.BitPaw;

import org.springframework.data.domain.Sort;

import javax.persistence.ManyToOne;

public class Room
{
    private Barrier[] _barrierList;
    private final Robot _robot;
    private final Point _roomMaxIndex;

    private boolean _ignoreBarrier = true;

    public Room()
    {
        final Point startPosition = new Point(0,2);
        final int width = 12;
        final int height = 9;

        _roomMaxIndex = new Point(width,height);

        _robot = new Robot(startPosition);


if(_ignoreBarrier)
{
    _barrierList = new Barrier[4];
    _barrierList[0] = new Barrier(3,0,3,3);
    _barrierList[1] = new Barrier(5,0,5,4);
    _barrierList[2] = new Barrier(4,5,7,5);
    _barrierList[3] = new Barrier(7,5,7,9);
}


    }

    private boolean Between(int a, int b, int value)
    {
        int max = Math.max(a, b);
        int min = Math.min(a, b);

        return min < value && value < max;
    }

    private MoveCommand GetNextPossibleMove(final MoveCommand move)
    {
        int nearestWall;
        int maximalSteps = Integer.MAX_VALUE;

        // Get worldBorder
        switch (move.Direction)
        {
            case Up:
                nearestWall = _roomMaxIndex.Y;
                break;

            case Down:
            case Left:
                nearestWall = -1;
                break;

            default:
            case Right:
                nearestWall = _roomMaxIndex.X;
                break;
        }

        for (Barrier barrier : _barrierList) // Go thru every barrier
        {
            if (move.Orientation != barrier.Orientation)  // Get only the barriers with opposite axis
            {
                // Are we in collidable direction?
                boolean canCollide = barrier.Collide(_robot.CurrentPosition, move.Orientation);

                if (canCollide)
                {
                    int barrierValue = barrier.GetWallOriantationValue();

                    // We can collide with the wall

                    // Check for range

                    if(Point.IsBNearer(_robot.CurrentPosition,nearestWall, barrierValue, move.Direction))
                    {
                        nearestWall = barrierValue;
                    }

                    /*
                    // Is the wall nearer to the object
                    if (move.IsPositiveDirection) {
                        if (nearestWall > barrierValue && Point.IsBNearer(_robot.CurrentPosition,nearestWall, barrierValue, move.Direction)) {
                            // Wall is nearer
                            nearestWall = barrierValue;
                        }
                    } else {
                        if (nearestWall < barrierValue && Point.IsBNearer(_robot.CurrentPosition,nearestWall,barrierValue, move.Direction)) {
                            // Wall is nearer
                            nearestWall = barrierValue;
                        }
                    }
*/


                }
            }
        }







            // Get worldBorder
            switch (move.Direction)
            {
                case Down:
                {
                    int wantedPos = _robot.CurrentPosition.Y - move.MoveLength;
                    int wallPos = nearestWall;
                    int movelength = move.MoveLength;

                    if(wantedPos < 0) // If we overflow at the bottom, all remaining fields are the move
                    {
                        movelength = _robot.CurrentPosition.Y;
                    }

                    if(wantedPos > wallPos)
                    {
                        maximalSteps = movelength + (wantedPos - wallPos);
                    }
                    else
                    {
                        maximalSteps = movelength;
                    }
                    break;
                }

                case Left:
                    {
                        int wantedPos = _robot.CurrentPosition.X - move.MoveLength;
                        int wallPos = nearestWall;
                        int movelength = move.MoveLength;

                        if(wantedPos < 0) // If we overflow at the bottom, all remaining fields are the move
                        {
                            movelength = _robot.CurrentPosition.X;
                        }

                        if(wantedPos > wallPos || Between(wantedPos, _robot.CurrentPosition.X, nearestWall))
                        {
                            maximalSteps = movelength + (wantedPos - wallPos);
                        }
                        else
                        {
                            maximalSteps = movelength;
                        }
                    break;
                }

                case Up:
                {
                    int wantedPos = _robot.CurrentPosition.Y + move.MoveLength;
                    int wallPos = nearestWall-1;
                    int movelength = move.MoveLength;

                    if(wantedPos > _roomMaxIndex.Y-1)
                    {
                        movelength = wantedPos - (wantedPos - _roomMaxIndex.Y-1);
                    }

                    if(wantedPos > wallPos)
                    {
                        maximalSteps = move.MoveLength - (wantedPos - wallPos);
                    }
                    else
                    {
                        maximalSteps = move.MoveLength;
                    }
                    break;
                }

                case Right:
                {
                    int wantedPos = _robot.CurrentPosition.X + move.MoveLength;
                    int wallPos = nearestWall-1;
                    int movelength = move.MoveLength;

                    if(wantedPos > _roomMaxIndex.X-1)
                    {
                        movelength = wantedPos - (wantedPos - _roomMaxIndex.X-1);
                    }

                    if(wantedPos > wallPos)
                    {
                        maximalSteps = move.MoveLength - (wantedPos - wallPos);
                    }
                    else
                    {
                        maximalSteps = move.MoveLength;
                    }
                    break;
                }
            }


        return new MoveCommand(move.Direction, maximalSteps);
    }

    public Point MoveRoboter(MoveCommand moveCommand)
    {
        final MoveCommand alteredMoveCommand = GetNextPossibleMove(moveCommand);
        final boolean hasChanged = moveCommand.equals(alteredMoveCommand);
        final Point movement = alteredMoveCommand.ConvertToPoint();
        final Point oldPosition =  new Point(_robot.CurrentPosition.X, _robot.CurrentPosition.Y);
        Point newPosition;

        _robot.CurrentPosition.Add(movement);

        newPosition =  _robot.CurrentPosition;

        System.out.println("====================");
        System.out.print("Wanted: " + moveCommand.toString());
        System.out.println(" CanDo: " + alteredMoveCommand.toString());
        System.out.print("Pos Before: " + oldPosition.toString());
        System.out.println(" After: " + newPosition.toString());

        return _robot.CurrentPosition;
    }
}