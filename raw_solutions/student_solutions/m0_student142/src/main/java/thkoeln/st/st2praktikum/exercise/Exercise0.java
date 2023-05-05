package thkoeln.st.st2praktikum.exercise;

public class  Exercise0 implements GoAble {

    int X_MAX = 12;
    int Y_MAX = 9;

    int xDroid = 0;
    int yDroid = 0;


    class Obstacle{
        boolean North = false;
        boolean South = false;
        boolean East = false;
        boolean West = false;
    }

    public Exercise0(){
        InitializeDeck();
    }

    public static void main(String[] args){
        Exercise0 ex = new Exercise0();
        System.out.println(ex.goTo("[so,5]"));
        System.out.println(ex.goTo("[ea,5]"));
        System.out.println(ex.goTo("[so,4]"));
        System.out.println(ex.goTo("[no,5]"));
    }

    Obstacle [][] obstaclesInDeck = new Obstacle[X_MAX][Y_MAX];

    public enum Direction{
        North, South, East, West, None
    }

    void goToDirection(Direction d, int steps){

        if (steps <= 0)
            return;

        int newX = xDroid;
        int newY = yDroid;

        switch (d){
            case North:
                newY = newY +1;
                break;
            case South:
                newY = newY-1;
                break;
            case East:
                newX = newX +1;
                break;
            case West:
                newX = newX-1;
                break;
        }

        if (newX < 0 || newX >= X_MAX || newY < 0 || newY >= Y_MAX)
            return;

        boolean obstacleExists = false;

        switch  (d){
            case North:
                obstacleExists = obstaclesInDeck[xDroid][yDroid].North ||
                        obstaclesInDeck[newX][newY].South;
                break;
            case South:
                obstacleExists = obstaclesInDeck[xDroid][yDroid].South ||
                        obstaclesInDeck[newX][newY].North;
                break;

            case West:
                obstacleExists = obstaclesInDeck[xDroid][yDroid].West ||
                        obstaclesInDeck[newX][newY].East;
                break;

            case East:
                obstacleExists = obstaclesInDeck[xDroid][yDroid].East ||
                        obstaclesInDeck[newX][newY].West;
                break;
        }
        if (!obstacleExists){
            xDroid = newX;
            yDroid = newY;
            goToDirection(d, steps-1);
        }else{
            // obstacle found. exit
        }
    }

    void InitializeDeck(){
        xDroid = 1;
        yDroid = 7;

        for( int i = 0; i < X_MAX; i++)
            for( int j = 0; j < Y_MAX; j++)
                obstaclesInDeck[i][j] = new Obstacle();



        for( int j = 3; j < Y_MAX; j++)
            obstaclesInDeck[3][j].West = true;

        obstaclesInDeck[3][2].North = true;
        obstaclesInDeck[4][2].North = true;

        obstaclesInDeck[4][0].East = true;
        obstaclesInDeck[4][1].East = true;

        obstaclesInDeck[5][0].East = true;
        obstaclesInDeck[5][1].East = true;
        obstaclesInDeck[5][2].East = true;
        obstaclesInDeck[5][3].East = true;

    }

    @Override
    public String goTo(String goCommandString) {
        boolean found = false;
        goCommandString = goCommandString.toLowerCase();
        String dirString = "";
        String stepString = "";

        int indexBrakeLeft = goCommandString.indexOf("[");
        if (indexBrakeLeft >=0){
            int indexComma = goCommandString.indexOf(",", indexBrakeLeft + 1);
            if (indexComma >= 0){
                dirString = goCommandString.substring(indexBrakeLeft +1, indexComma);
                int indexBrakeRight = goCommandString.indexOf("]", indexComma+1);
                if (indexBrakeRight >= 0){
                    stepString = goCommandString.substring(indexComma + 1, indexBrakeRight);
                    found = true;
                }
            }
        }

        if (found){
            Direction d = Direction.None;
            switch(dirString){
                case "no": d = Direction.North; break;
                case "ea": d = Direction.East; break;
                case "so": d = Direction.South; break;
                case "su": d = Direction.South; break;
                case "we": d = Direction.West; break;
            }
            int steps = Integer.parseInt(stepString);
            goToDirection(d,steps);
        }

        String newPosition = "(" + xDroid + "," + yDroid + ")";
        return newPosition;

    }

}

