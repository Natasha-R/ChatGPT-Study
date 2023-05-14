package thkoeln.st.st2praktikum.exercise;


public class Obstacle {

    private Coordinate start;
    private Coordinate end;


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        if(pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
            throw new RuntimeException("Obstacle cant be diagonal");

        if(pos1.getX() > pos2.getX() || pos1.getY() > pos2.getY()) {
            this.start = pos2;
            this.end = pos1;
        }
        else {
            this.start = pos1;
            this.end = pos2;
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {

            if(obstacleString.split("-").length != 2)
                throw new RuntimeException("command needs to be seperated with '-'");

            String[] sourceDestinationSplit = obstacleString.split("-");

            if(sourceDestinationSplit[0].split(",").length != 2)
                throw new RuntimeException("left 2 numbers must be seperated with ',");

            String[] sourceXYSplit = sourceDestinationSplit[0].split(",");

            if(!sourceXYSplit[0].startsWith("("))
                throw new RuntimeException("left command must start with '('");

            Integer sourceX = Integer.parseInt(sourceXYSplit[0].substring(1));

            if(!sourceXYSplit[1].endsWith(")"))
                throw new RuntimeException("left command must end with ')'");

            Integer sourceY = Integer.parseInt(sourceXYSplit[1].substring(0, sourceXYSplit[1].length() - 1));

            if(sourceDestinationSplit[1].split(",").length != 2) {
                throw new RuntimeException("right 2 numbers must be seperated with ','");
            }
            String[] destXYSplit = sourceDestinationSplit[1].split(",");

            if(!destXYSplit[0].startsWith("("))
                throw new RuntimeException("Right command must start with '('");

            Integer destX = Integer.parseInt(destXYSplit[0].substring(1));

            if(!destXYSplit[1].endsWith(")"))
                throw new RuntimeException("Right command must end with ')'");

            Integer destY = Integer.parseInt(destXYSplit[1].substring(0, 1));

            if(destX != sourceX && sourceY != sourceY)
                throw new RuntimeException("Obstacle cant be diagonal");


            Coordinate startCoordinate = new Coordinate(sourceX,sourceY);
            Coordinate endCoordinate = new Coordinate(destX,destY);

            if(sourceX > destX || sourceY > destY) {
                this.start = endCoordinate;
                this.end = startCoordinate;
            }
            else {
                this.start = startCoordinate;
                this.end = endCoordinate;
            }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
