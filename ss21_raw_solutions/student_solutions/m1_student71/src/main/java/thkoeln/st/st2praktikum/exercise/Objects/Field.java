package thkoeln.st.st2praktikum.exercise.Objects;

import thkoeln.st.st2praktikum.exercise.Exception.SpawnMiningMachineException;

import javax.management.InvalidAttributeValueException;
import java.util.HashMap;
import java.util.UUID;

public class Field extends BaseClass {

    private final int height;
    private final int width;

    public Tile[][] board;

    public HashMap<UUID, Obstacle> ObstacleList = new HashMap<UUID, Obstacle>();

    public Field(int height, int width) {
        super();
        this.height = height;
        this.width = width;

        board = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                var tempTile = new Tile();

                if(y == 0)
                    tempTile.North = new Obstacle();
                if(y == height - 1)
                    tempTile.South = new Obstacle();
                if(x == 0)
                    tempTile.West = new Obstacle();
                if(x == width - 1)
                    tempTile.East = new Obstacle();

                board[x][y] = tempTile;
            }
        }
    }

    public Tile getTile(Coordinates coordinates){
        return getTile(coordinates.X,coordinates.Y);
    }

    //Gets tile in ST2 coordinate system
    public Tile getTile(int st2x, int st2y){
        return board[st2x][height-st2y -1];
    }

    //Adds obstacle "(0,3)-(2,3)" -> 0,3 to 2,3
    public void addObstacle(String obstacleString) throws InvalidAttributeValueException {
        var coordinate1 = new Coordinates(obstacleString.split("-")[0]);
        var coordinate2 = new Coordinates(obstacleString.split("-")[1]);

        addObstacle(coordinate1,coordinate2);
    }

    public void addObstacle(Coordinates point1, Coordinates point2){

        //draw line vertical
        if(point1.X == point2.X){
            var x = point1.X;
            var fromY = (point1.Y < point2.Y) ? point1.Y : point2.Y;
            var toY = (point1.Y < point2.Y) ? point2.Y : point1.Y;

            for (int y = fromY; y < toY; y++) {
                getTile(x,y).West = new Obstacle();
                getTile(x-1,y).East = new Obstacle();
            }
        }

        //draw line horizontal
        if(point1.Y == point2.Y){
            var y = point1.Y;
            var fromX = (point1.X < point2.X) ? point1.X : point2.X;
            var toX = (point1.X < point2.X) ? point2.X : point1.X;

            for (int x = fromX; x < toX; x++) {
                getTile(x,y).South = new Obstacle();
                getTile(x,y-1).North = new Obstacle();
            }
        }

    }

    public void spawnMiningMachine(MiningMachine miningMachine, Coordinates coordinates) throws SpawnMiningMachineException {
        var tile = getTile(coordinates.X,coordinates.Y);

        if(tile.isOccupied())
            throw new SpawnMiningMachineException("Tile is already occupied");

        miningMachine.changePosition(this, coordinates);

        tile.Content = miningMachine;
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
        getTile(connection.SourceCoordinates).connection = connection;
    }
}
