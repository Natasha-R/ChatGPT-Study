package thkoeln.st.st2praktikum.exercise;

import javax.management.InvalidAttributeValueException;

public class Field extends UUIDBase {

    private final int height;
    private final int width;

    private Tile[][] board;

    public Field(int height, int width) {
        super();
        this.height = height;
        this.width = width;

        board = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                var tempTile = new Tile();

                if(y == 0)
                    tempTile.setNorth(true);
                if(y == height - 1)
                    tempTile.setSouth(true);
                if(x == 0)
                    tempTile.setWest(true);
                if(x == width - 1)
                    tempTile.setEast(true);

                board[x][y] = tempTile;
            }
        }
    }

    public Tile getTile(Coordinate coordinates){
        return getTile(coordinates.getX(),coordinates.getY());
    }

    //Gets tile in ST2 coordinate system
    public Tile getTile(int st2x, int st2y){
        return board[st2x][height-st2y -1];
    }

    //Adds obstacle "(0,3)-(2,3)" -> 0,3 to 2,3
    public void addObstacle(Obstacle obstacle) throws InvalidAttributeValueException {
        addObstacle(obstacle.getStart(),obstacle.getEnd());
    }

    public void addObstacle(Coordinate point1, Coordinate point2){

        //draw line vertical
        if(point1.getX() == point2.getX()){
            var x = point1.getX();
            var fromY = (point1.getY() < point2.getY()) ? point1.getY() : point2.getY();
            var toY = (point1.getY() < point2.getY()) ? point2.getY() : point1.getY();

            for (int y = fromY; y < toY; y++) {
                getTile(x,y).setWest(true);
                getTile(x-1,y).setEast(true);
            }
        }

        //draw line horizontal
        if(point1.getY() == point2.getY()){
            var y = point1.getY();
            var fromX = (point1.getX() < point2.getX()) ? point1.getX() : point2.getX();
            var toX = (point1.getX() < point2.getX()) ? point2.getX() : point1.getX();

            for (int x = fromX; x < toX; x++) {
                getTile(x,y).setSouth(true);
                getTile(x,y-1).setNorth(true);
            }
        }

    }

    public void spawnMiningMachine(MiningMachine miningMachine, Coordinate coordinates) throws SpawnMiningMachineException {
        var tile = getTile(coordinates.getX(),coordinates.getY());

        if(tile.isOccupied())
            throw new SpawnMiningMachineException("Tile is already occupied");

        miningMachine.changePosition(this, coordinates);

        tile.setContent(miningMachine);
    }

    @Override
    public String toString() {
        String returnString = "";

        for (int y = 0; y < height; y++) {
            var line1String = "";
            var line2String = "";
            var line3String = "";

            for (int x = 0; x < width; x++) {
                var muLineCharArray = board[x][y].toString().toCharArray();

                line1String += " " + muLineCharArray[0] + " ";
                line2String += "" + muLineCharArray[1] + muLineCharArray[2] + muLineCharArray[3];
                line3String += " " + muLineCharArray[4] + " ";

            }
            returnString += line1String+ "\n" +line2String+ "\n" +line3String+ "\n";
        }

        return returnString;
    }


    public void setConnection(Connection connection) {
        getTile(connection.getSourceCoordinates()).setConnection(connection);
    }
}
