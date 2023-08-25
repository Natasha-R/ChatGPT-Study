package thkoeln.st.st2praktikum.exercise.Objects;

import thkoeln.st.st2praktikum.exercise.Exception.TeleportMiningMachineException;

public class MiningMachine extends BaseClass{

    public String Name;
    public Field CurrentField;
    public Coordinates Position;


    public MiningMachine(String name){
        Name = name;
    }

    public void changePosition(Field field, Coordinates coordinates){
        CurrentField = field;
        Position = coordinates;
    }

    public Tile getCurrentTile(){
        return CurrentField.getTile(Position);
    }

    public void moveNorth(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = CurrentField.getTile(Position);

            if(!currentTile.isNorthWalkable())
                return;

            var moveTile = CurrentField.getTile(Position.X,Position.Y+1);

            if(moveTile.isOccupied())
                return;

            currentTile.Content = null;
            moveTile.Content = this;

            Position.Y++;
        }
    }

    public void moveEast(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = CurrentField.getTile(Position);

            if(!currentTile.isEastWalkable())
                return;

            var moveTile = CurrentField.getTile(Position.X+1,Position.Y);

            if(moveTile.isOccupied())
                return;

            currentTile.Content = null;
            moveTile.Content = this;

            Position.X++;
        }
    }

    public void moveSouth(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = CurrentField.getTile(Position);

            if(!currentTile.isSouthWalkable())
                return;

            var moveTile = CurrentField.getTile(Position.X,Position.Y-1);

            if(moveTile.isOccupied())
                return;

            currentTile.Content = null;
            moveTile.Content = this;

            Position.Y--;
        }
    }

    public void moveWest(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = CurrentField.getTile(Position);

            if(!currentTile.isWestWalkable())
                return;

            var moveTile = CurrentField.getTile(Position.X-1,Position.Y);

            if(moveTile.isOccupied())
                return;

            currentTile.Content = null;
            moveTile.Content = this;

            Position.X--;
        }
    }

    public void teleport(Connection connection) throws TeleportMiningMachineException,NullPointerException {
        if(connection == null)
            throw new NullPointerException();

        if(CurrentField.Uuid != connection.SourceField.Uuid)
            throw new TeleportMiningMachineException("MiningMachine isn't on sourceField");

        if(!Position.equals(connection.SourceCoordinates))
            throw new TeleportMiningMachineException("MiningMachine isn't on sourceCoordinate");

        var destinationTile = connection.DestinationField.getTile(connection.DestinationCoordinates);

        if(destinationTile.isOccupied())
            throw new TeleportMiningMachineException("Tile is already occupied!");

        var currentTile = CurrentField.getTile(Position);
        currentTile.Content = null;
        destinationTile.Content = this;

        changePosition(connection.DestinationField, connection.DestinationCoordinates);

    }
}
