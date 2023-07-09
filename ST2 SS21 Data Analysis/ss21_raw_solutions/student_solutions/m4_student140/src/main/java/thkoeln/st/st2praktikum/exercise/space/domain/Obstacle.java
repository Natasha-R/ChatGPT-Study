package thkoeln.st.st2praktikum.exercise.space.domain;


import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

public class Obstacle {

    private Coordinate start;
    private Coordinate end;


    public Obstacle(Coordinate start, Coordinate end) {
        if(!start.getX().equals( end.getX() ) && !start.getY().equals( end.getY() ) )
            throw new RuntimeException("Obstacle cant be diagonal");

        if( start.getX() > end.getX() || start.getY() > end.getY() ) {
            this.start = end;
            this.end = start;
        }
        else {
            this.start = start;
            this.end = end;
        }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        if( obstacleString.split("-").length != 2)
            throw new RuntimeException("command needs to be seperated with '-'");

        String[] sourceDestinationSplit = obstacleString.split("-");

        if( sourceDestinationSplit[0].split(",").length != 2 )
            throw new RuntimeException("left 2 numbers must be seperated with ',");

        String[] sourceXYSplit = sourceDestinationSplit[ 0 ].split(",");

        if(!sourceXYSplit[0].startsWith("("))
            throw new RuntimeException("left command must start with '('");

        Integer sourceX = Integer.parseInt(sourceXYSplit[ 0 ].substring( 1 ) );

        if(!sourceXYSplit[1].endsWith(")"))
            throw new RuntimeException("left command must end with ')'");

        Integer sourceY = Integer.parseInt(sourceXYSplit[ 1 ].substring( 0 , sourceXYSplit[ 1 ].length() - 1) );

        if(sourceDestinationSplit[ 1 ].split(",").length != 2) {
            throw new RuntimeException("right 2 numbers must be seperated with ','");
        }
        String[] destXYSplit = sourceDestinationSplit[ 1 ].split(",");

        if(!destXYSplit[0].startsWith("("))
            throw new RuntimeException("Right command must start with '('");

        Integer destX = Integer.parseInt(destXYSplit[ 0 ].substring( 1 ) );

        if(!destXYSplit[ 1 ].endsWith(")"))
            throw new RuntimeException("Right command must end with ')'");

        Integer destY = Integer.parseInt(destXYSplit[ 1 ].substring( 0 , 1 ) );


        Coordinate startCoordinate = new Coordinate(sourceX,sourceY);
        Coordinate endCoordinate = new Coordinate(destX,destY);

        if(sourceX > destX || sourceY > destY) {
            return new Obstacle(endCoordinate, startCoordinate);
        }
        else {
            return new Obstacle(startCoordinate, endCoordinate);
        }
    }
}
