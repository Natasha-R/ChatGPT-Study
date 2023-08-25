package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private int coordinateX = 3, coordinateY = 0;

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    private void checkIterationNorthWallSmash(int coordinateX, int coordinateY, int actionCount)
    {
        // under the first wall
        if (coordinateY <= 3 && (coordinateX >= 4 && coordinateX <=7))
        {
            coordinateY+=actionCount;

            if (coordinateY >= 3)
                coordinateY = 2;
            setCoordinateY(coordinateY);

            // between wall 1 & 2
        }else if (coordinateY == 3 && coordinateX >= 1 && coordinateX <= 7)
        {
            coordinateY = 3;
            setCoordinateY(coordinateY);


        }else if (coordinateY <= 3 && (coordinateX >= 1 && coordinateX <= 7) && !(coordinateX >= 4 && coordinateX <=7) )
        {
          coordinateY +=actionCount;
          if (coordinateY > 3)
          {
              coordinateY = 3;
          }
          setCoordinateY(coordinateY);

        } else if (coordinateY == 3 && !(coordinateX >= 1 && coordinateX <= 8))
        {

            coordinateY+=actionCount;
            if (coordinateY > 11)
                coordinateY = 11;
                setCoordinateY(coordinateY);
        } else
        {
            // abroad the two walls ==> boundary check
            coordinateY += actionCount;
            if (coordinateY > 7)
                coordinateY = 7;
                setCoordinateY(coordinateY);
        }
    }


    private void checkIterationEastWallSmash(int coordinateX, int coordinateY, int actionCount)
    {
        // before the first wall
        if (coordinateX <= 2 && (coordinateY >= 0 && coordinateY <= 2))
        {
            coordinateX += actionCount;

            if (coordinateX >= 3)
            {
                coordinateX = 2;

            }

            setCoordinateX(coordinateX);
            // between wall 1 & 2
        }else if (coordinateX >= 3 && coordinateX <= 7 &&  (coordinateY >= 0 && coordinateY <= 1))
        {

            coordinateX+=actionCount;

            if (coordinateX >= 7 )
            {
                coordinateX = 6;

            }
            setCoordinateX(coordinateX);

            // abroad the walls ==> boundary check
        }else
        {
            coordinateX += actionCount;
            if (coordinateX >= 11)
                coordinateX = 11;

            setCoordinateX(coordinateX);
        }
    }

    private void checkIterationSouthWallSmash(int coordinateX, int coordinateY, int actionCount)
    {
        if (coordinateY >= 4 && (coordinateX >= 1 && coordinateX <= 7))
        {
            coordinateY-=actionCount;
            if (coordinateY <= 4)
            {
                coordinateY = 4;
            }
            setCoordinateY(coordinateY);

        }else if (coordinateY == 3 && (coordinateX >= 4 && coordinateX <=6) )
        {

            coordinateY = 3;
            setCoordinateY(coordinateY);

        }else if (coordinateY == 3 && !(coordinateX >= 4 && coordinateX <=7))
        {
            coordinateY-=actionCount;
            if (coordinateY < 0)
                coordinateY = 0;

            setCoordinateY(coordinateY);
        }else
        {
            coordinateY-=actionCount;
            if (coordinateY < 0)
                coordinateY = 0;

            setCoordinateY(coordinateY);
        }

    }

    private void checkIterationWestWallSmash(int coordinateX, int coordinateY, int actionCount)
    {
        if (coordinateX >= 7 && (coordinateY >= 0 && coordinateY <= 1))
        {
            coordinateX -= actionCount;

            if (coordinateX <= 7)
            {
                coordinateX = 7;
            }

            setCoordinateX(coordinateX);

        }else if (coordinateX <= 7 && coordinateX >= 3 && (coordinateY <= 2))
        {
            coordinateX -= actionCount;

            if (coordinateX <= 3)
            {
                coordinateX = 3;
            }

            setCoordinateX(coordinateX);



        }else if (coordinateX <= 7 && coordinateX >= 3 && coordinateY == 3)
        {

            coordinateX -= actionCount;

            if (coordinateX <= 0)
            {
                coordinateX = 0;
            }

            setCoordinateX(coordinateX);
        } else if (coordinateX <= 7 && coordinateX >= 3 && !(coordinateY >= 0 && coordinateY <= 3))
        {
            coordinateX-=actionCount;

            setCoordinateX(coordinateX);
        }else
        {
            coordinateX-=actionCount;
            if (coordinateX < 0)
            {
                coordinateX = 0;
            }

            setCoordinateX(coordinateX);
        }


    }

    @Override
    public String moveTo(String moveCommandString)
    {

        moveCommandString = moveCommandString.replace("[","");

        moveCommandString = moveCommandString.replace("]","");

        String[] movementCommand = moveCommandString.split(",");

        String count = movementCommand[1];

        int actionCount = Integer.parseInt(count);

        String movementOrientation = movementCommand[0];


        switch (movementOrientation)
        {
            case "no":
                checkIterationNorthWallSmash(coordinateX,coordinateY,actionCount);
                break;
            case "ea":
                checkIterationEastWallSmash(coordinateX,coordinateY,actionCount);
                break;
            case "so":
                checkIterationSouthWallSmash(coordinateX,coordinateY,actionCount);
                break;
            case "we":
                checkIterationWestWallSmash(coordinateX,coordinateY,actionCount);
                break;
        }





          return "("+coordinateX+","+coordinateY+")";

    }

}

