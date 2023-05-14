package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    enum Direction {NORTH, SOUTH, EAST, WEST};
    class Coordinate {
        public int x;
        public int y;
        public Coordinate(int x, int y){this.x = x; this.y = y;}
        @Override
        public String toString() { return "(" + x + "," + y + ')'; }
    }

    class Wall {
        public Coordinate start;
        public Coordinate end;
        public Wall(Coordinate start, Coordinate end){this.start = start; this.end = end;}
    }

    Wall [] walls;
    Coordinate miningMachine;

    // little helper function to look if the mining machine
    // is at the same level as a wall
    private boolean between(int i, int first, int second) {
        int min = Integer.min(first,second);
        int max = Integer.max(first,second);
        return  (i >= min && i < max) ;
    }

    private void moveMachine(Direction direction, int steps){

        // movement boundaries for the mining machine
        int maxRight = Integer.MAX_VALUE;
        int maxLeft = Integer.MIN_VALUE;
        int maxTop = Integer.MAX_VALUE;
        int maxBottom = Integer.MIN_VALUE;

        // get the smallest bound
        for(Wall wall : walls){
            // if there is a wall at the same ground area
            if(between(miningMachine.x,wall.end.x,wall.start.x)){
                // get the vertical distance
                int distance = wall.start.y - miningMachine.y;
                if(distance > 0){ // wall at the top
                    distance--; // correction due to coordinate at bottom left corner
                    if(distance < maxTop){ maxTop = distance;}
                } else {  // wall at bottom
                    if(distance > maxBottom){maxBottom = distance;}
                }
            }
            // if there is a wall at the same height
            if(between(miningMachine.y,wall.end.y, wall.start.y)){
                int distance = wall.start.x - miningMachine.x;
                if(distance > 0){ // wall at right
                    distance--; // correction due to coordinate at bottom left corner
                    if(distance < maxRight){maxRight=distance;}
                } else {
                    if(distance > maxLeft){maxLeft=distance;}
                }
            }
        }

        // do the steps for the mining machine
        switch (direction){
            case NORTH:
                int stepsUp = Integer.min(maxTop,steps);
                miningMachine.y += stepsUp;
                break;
            case EAST:
                int stepsRight = Integer.min(maxRight,steps);
                miningMachine.x += stepsRight;
                break;
            case WEST:
                int stepsLeft = Integer.min(-maxLeft,steps);
                miningMachine.x -= stepsLeft;
                break;
            case SOUTH:
                int stepsDown = Integer.min(-maxBottom,steps);
                miningMachine.y -= stepsDown;
        }
    }

    public Exercise0() {
        miningMachine = new Coordinate(11,7);
        walls = new Wall [] {
                // boundaries
                new Wall(new Coordinate(0,0), new Coordinate(12,0)),  // bottom
                new Wall(new Coordinate(0, 8), new Coordinate(12,8)), // top
                new Wall(new Coordinate(0,0), new Coordinate(0,8)),   // left
                new Wall(new Coordinate(12,0), new Coordinate(12,8)), // right

                // normal walls
                new Wall(new Coordinate(6,2), new Coordinate(6,5)),
                new Wall(new Coordinate(5,5), new Coordinate(6,5)),
                new Wall(new Coordinate(5,5), new Coordinate(5,6)),
                new Wall(new Coordinate(5,6), new Coordinate(12,6))
        };
    }



    @Override
    public String walkTo(String walkCommandString) {

        // parse string somehow
        // get steps
        int steps = Integer.parseInt(walkCommandString.replaceAll("[^0-9]",""));
        // get direction
        Direction direction;
        String str = walkCommandString.replaceAll("[\\[|\\]|0-9]|\\,","");
        if(str.equals("we")){ direction = Direction.WEST;}
        else if(str.equals("no")){direction = Direction.NORTH;}
        else if(str.equals("so")){direction = Direction.SOUTH;}
        else if(str.equals("ea")){direction = Direction.EAST;}
        else { throw new IllegalArgumentException("couldn't parse string argument for walkTo"); }

        // do the movement
        moveMachine(direction,steps);

        return miningMachine.toString();
    }
}
