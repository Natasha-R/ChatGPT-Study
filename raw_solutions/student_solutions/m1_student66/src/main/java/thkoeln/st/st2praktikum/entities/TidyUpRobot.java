package thkoeln.st.st2praktikum.entities;

import thkoeln.st.st2praktikum.exceptions.UnknownActionException;
import thkoeln.st.st2praktikum.interfaces.*;

public class TidyUpRobot extends AbstractEntity implements GoAble, TransportAble, InitAble, HitAble {

    private String name;
    private Room room;
    private Cell position;

    public TidyUpRobot(String name){
        this.name = name;
    }

    @Override
    public String go(String goCommandString) {
        Command command = new Command(goCommandString);
        int steps = Integer.parseInt(command.getArgument());
        Cell newPosition;
        for(int i = 0; i < steps; i++) {
            switch (command.getAction()) {
                case "no":
                    newPosition = new Cell(position.getX(), position.getY() + 1);
                    break;
                case "so":
                    newPosition = new Cell(position.getX(), position.getY() - 1);
                    break;
                case "we":
                    newPosition = new Cell(position.getX() - 1, position.getY());
                    break;
                case "ea":
                    newPosition = new Cell(position.getX() + 1, position.getY());
                    break;
                default:
                    throw new UnknownActionException();
            }

            if(!room.isInsideLimit(newPosition))
                return position.toString();

            for(HitAble hitAble : room.getHitAbles()){
                if(hitAble.isHitByMove(position, newPosition))
                    return position.toString();
            }
            position = newPosition;
        }
        return position.toString();
    }

    @Override
    public Boolean isHitByMove(Cell sourceCell, Cell destinationCell) {
        return destinationCell.equals(position);
    }

    @Override
    public Boolean init(Room initialRoom) {
        if(getPosition() == null) {
            for(HitAble hitAble : initialRoom.getHitAbles()){
                if(hitAble.isHitByMove(new Cell("(0,0)"), new Cell("(0,0)")))
                    return false;
            }
            room = initialRoom;
            position = new Cell("(0,0)");
            room.addHitAble(this);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean transport(Room destinationRoom){
        for(UseAble useAble : room.getUseAbles()){
            if(useAble instanceof Connection){
                Connection connection = (Connection) useAble;
                if(connection.getSourceCell().equals(position)){
                    if(connection.isUseable()) {
                        room.removeHitAble(this);
                        destinationRoom.addHitAble(this);
                        room = destinationRoom;
                        position = connection.getDestinationCell();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getName(){
        return name;
    }

    public Cell getPosition() {
        return position;
    }

    public Room getRoom() {
        return room;
    }
}
