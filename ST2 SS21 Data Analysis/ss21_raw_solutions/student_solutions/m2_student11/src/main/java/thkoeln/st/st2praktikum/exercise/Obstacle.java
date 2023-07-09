package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.ObstacleFormatException;

import java.util.ArrayList;

public class Obstacle {

    private Coordinate start;
    private Coordinate end;

    private ArrayList<Coordinate> Borders = new ArrayList<>();
    //private DirectionType direction;

    public Obstacle(Coordinate pos1, Coordinate pos2) {
        if(order(pos1, pos2)){
            this.start = pos1;
            this.end = pos2;
        }else{
            this.start = pos2;
            this.end = pos1;
        }

        addObstacle(pos1.toString() + "-" + pos2.toString());
        if(getDirection(putCoordinates(pos1.toString() + "," + pos2.toString())) == null ) throw new ObstacleFormatException();
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        String[] separatedCords = obstacleString.split("-");
        try {
            if(order( new Coordinate(separatedCords[0]), new Coordinate(separatedCords[1]))){
                this.start = new Coordinate(separatedCords[0]);
                this.end = new Coordinate(separatedCords[1]);
            }else{
                this.end = new Coordinate(separatedCords[0]);
                this.start = new Coordinate(separatedCords[1]);
            }

        }catch ( Exception e){
            throw new ObstacleFormatException();
        }
        //detects diagonal inputs
        if(getDirection(putCoordinates(obstacleString)) == null || !obstacleFormatTest(obstacleString)){
            throw new ObstacleFormatException();
        }
        addObstacle(obstacleString);
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public void addObstacle(String Coordinates){
        int[] CoordinatePair = putCoordinates(Coordinates);
        //get the direction of border and counter variable depending on the variable that changes when placing the border
        int start = 0, finish = 0;
        DirectionsType direction = getDirection(CoordinatePair);
        if(direction == null) return; //there was an issue getting the direction
        if(direction == DirectionsType.NORTH || direction == DirectionsType.SOUTH){ //what changes is the y coordinate
            if(CoordinatePair[1] < CoordinatePair[3]){
                start = CoordinatePair[1]; finish = CoordinatePair[3];
            }else{
                start = CoordinatePair[3]; finish = CoordinatePair[1];
            }
        }else if(direction == DirectionsType.EAST || direction == DirectionsType.WEST){
            if(CoordinatePair[0] < CoordinatePair[2]){
                start = CoordinatePair[0]; finish = CoordinatePair[2];
            }else{
                start = CoordinatePair[2]; finish = CoordinatePair[0];
            }
        }
        /**
         add borders to each frame with the direction of the obstacle
         direction of border -> what now matters is vertical or horizontal
         obstacle goes east/west, but the obstacle will be placed either north or south in the frame
         same for north/south, but with the borders placed east and west in the frames
         (you need the coordinate that changes, the other gives direction)
         */
        for(int i = start; i < finish; i++) {
            Coordinate entry1;
            Coordinate entry2;
            //vertical
            if(direction == DirectionsType.NORTH || direction == DirectionsType.SOUTH){
                //adding border in east frame of border
                entry1 = new Coordinate(CoordinatePair[0] - 1, i);
                entry1.border = DirectionsType.EAST;
                //adding border in west frame of border
                entry2 = new Coordinate(CoordinatePair[0], i);
                entry2.border = DirectionsType.WEST;
            }
            //horizontal
            else{
                //adding border in south frame
                entry1 = new Coordinate(i,CoordinatePair[1]);
                entry1.border = DirectionsType.SOUTH;
                //adding border in north frame
                entry2 = new Coordinate(i, CoordinatePair[1]-1);
                entry2.border = DirectionsType.NORTH;
            }
            Borders.add(entry1);
            Borders.add(entry2);
        }
    }
    //get direction the border is heading: north 0, south 1, east 2, west 3
    private DirectionsType getDirection(int[] Coordinates){
        if(Coordinates.length == 4) {
            int x1 = Coordinates[0];
            int y1 = Coordinates[1];
            int x2 = Coordinates[2];
            int y2 = Coordinates[3];
            //north or south
            if(x1 == x2){
                if(y1 < y2) return DirectionsType.NORTH;   //heading north
                else return DirectionsType.SOUTH;          //heading south
            }
            //west or east
            else if(y1 == y2){
                if(x1 < x2) return DirectionsType.EAST;   //heading east
                else return DirectionsType.WEST;          //heading west
            }
        }
        return null;
    }
    //get coordinates in list of x1,y1,x2,y2 for creating borders
    private int[] putCoordinates(String Coordinates){
        int[] CoordinatePair = new int[4];
        Coordinates = Coordinates.replace("(","").replace(")","").replace("-",",");
        String[] separatedCords = Coordinates.split(",");
        CoordinatePair[0] = Integer.parseInt(separatedCords[0]);
        CoordinatePair[1] = Integer.parseInt(separatedCords[1]);
        CoordinatePair[2] = Integer.parseInt(separatedCords[2]);
        CoordinatePair[3] = Integer.parseInt(separatedCords[3]);
        return CoordinatePair;
    }

    public boolean blocksCoordinateInDirection(Coordinate cord, DirectionsType direction){
        for(thkoeln.st.st2praktikum.exercise.Coordinate Coordinate : Borders){
            if(cord.equals(Coordinate) && Coordinate.border.equals(direction)){
                return true;
            }
        }

        return false;
    }//       012345678910
    // Valid: (1,2)-(1,4)
    //returns true if format is right
    private boolean obstacleFormatTest(String Input){
        if(Input.charAt(0) == Input.charAt(6) && Input.charAt(4) == Input.charAt(10) && Input.charAt(2) == Input.charAt(8) && Input.charAt(5) == '-'){
            return true;
        }
        return false;

    }
    //returns true if order is right, else false
    private boolean order(Coordinate first, Coordinate second){
        //closer to the bottom left corner-> start has the lowest x and y coordinate
        if(first.getX() < second.getX()){
            if(first.getY() < second.getY()){ //first hat die kleinere x und y
                return true;
            }
            else{ //first hat kleineres x aber größeres y
                return true;
            }
        }
        else if(first.getX() > second.getX()){
            if(first.getY() < second.getY()){ //second hat kleineres x und first kleineres y
                return false;
            }
            else{
                return false; //second hat kleineres x und größeres y
            }
        }
        else{ //x ist gleich
            if(first.getY() < second.getY()) return true;
            else return false;
        }

    }
}
