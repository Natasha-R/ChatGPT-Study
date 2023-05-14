package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.Movable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.Robot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.RoomInitializable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.Transportable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TidyUpRobot extends AbstractEntity implements Robot, RoomInitializable, Transportable, Movable {
    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private UUID roomId;

    @Setter
    @Getter
    @ManyToOne
    private Room currentRoom;

    @Setter
    @Getter
    @Embedded
    private Vector2D vector2D;

    @Setter
    @Getter
    @ElementCollection(targetClass = Command.class)
    @JoinColumn
    @JoinTable
    private List<Command> commands;

    public TidyUpRobot(){
        this.commands = new ArrayList<Command>();
    }

    @Override
    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void deleteCommand() {
        commands.clear();
    }

    @Override
    public Boolean roomInitialize(Room room, Command command) {
        if(!room.getCellOccupied().contains(new Vector2D(0,0))){
            setRoomId(room.getId());
            setCurrentRoom(room);
            room.addCellOccupied(new Vector2D(0,0));
            setVector2D(new Vector2D(0,0));
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean transportTo(Room currentRoom, Room destinationRoom, Command command) {
        Integer x = currentRoom.getConnection(destinationRoom.getId()).getDestinationCoordinate().getX();
        Integer y = currentRoom.getConnection(destinationRoom.getId()).getDestinationCoordinate().getY();

        if(!destinationRoom.getCellOccupied().contains(new Vector2D(x,y))){
            currentRoom.getCellOccupied().remove(vector2D);
            setRoomId(destinationRoom.getId());
            setVector2D(new Vector2D(x,y));
            destinationRoom.addCellOccupied(new Vector2D(x,y));
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public Boolean robotCommandMove(Room room, Command command){
        if(command.getCommandType().equals(CommandType.EAST)) return moveEast(room, command);
        else if(command.getCommandType().equals(CommandType.NORTH)) return moveNorth(room, command);
        else if(command.getCommandType().equals(CommandType.SOUTH)) return moveSouth(room, command);
        else return moveWest(room, command);
    }

    @Override
    public Boolean moveEast(Room room, Command command) {
        Integer x1 = vector2D.getX() + command.getNumberOfSteps();
        Integer x2 = null; //indicate, whether there is another robot or not

        for(int j=0; j<room.getCellOccupied().size(); j++){
            if(room.getCellOccupied().get(j).getX() > vector2D.getX() && room.getCellOccupied().get(j).getY().equals(vector2D.getY())){
                x2 = room.getCellOccupied().get(j).getX();
                break;
            }
        }
        if(x1 >= room.getWidth()) x1 = room.getWidth() - 1;
        if(room.getObstacles().size() == 0){
            if(x2 != null && x1 >= x2) x1 = x2 - 1;
        }

        for(int i=0; i<room.getObstacles().size(); i++){
            if(room.getObstacles().get(i).getStart().getX().equals(room.getObstacles().get(i).getEnd().getX())){
                if(x2 != null && x1 >= x2 && x2 < room.getObstacles().get(i).getStart().getX()){
                    x1 = x2 - 1;
                }
                else if(vector2D.getY() >= room.getObstacles().get(i).getStart().getY() && vector2D.getY() < room.getObstacles().get(i).getEnd().getY()){
                    if(x1 >= room.getObstacles().get(i).getStart().getX() && vector2D.getX() < room.getObstacles().get(i).getStart().getX())
                        x1 = room.getObstacles().get(i).getStart().getX() - 1;
                }
            }
        }

        room.getCellOccupied().remove(vector2D);
        setVector2D(new Vector2D(x1, vector2D.getY()));
        room.addCellOccupied(vector2D);
        return Boolean.TRUE;
    }

    @Override
    public Boolean moveNorth(Room room, Command command) {
        Integer y1 = vector2D.getY() + command.getNumberOfSteps();
        Integer y2 = null;

        for(int j = 0; j<room.getCellOccupied().size(); j++){
            if(room.getCellOccupied().get(j).getY() > vector2D.getY() && room.getCellOccupied().get(j).getX().equals(vector2D.getX())){
                y2 = room.getCellOccupied().get(j).getY();
                break;
            }
        }
        if(y1 >= room.getHeight()) y1 = room.getHeight() - 1;
        if(room.getObstacles().size() == 0){
            if(y2 != null && y1 >= y2) y1 = y2 - 1;
        }

        for(int i=0; i<room.getObstacles().size(); i++){
            if(room.getObstacles().get(i).getStart().getY().equals(room.getObstacles().get(i).getEnd().getY())){
                if(y2 != null && y1 >= y2 && y2 < room.getObstacles().get(i).getStart().getY())
                    y1 = y2 - 1;
                else if(vector2D.getX() >= room.getObstacles().get(i).getStart().getX() && vector2D.getX() < room.getObstacles().get(i).getEnd().getX()){
                    if(y1 >= room.getObstacles().get(i).getStart().getY() && vector2D.getY() < room.getObstacles().get(i).getStart().getY())
                        y1 = room.getObstacles().get(i).getStart().getY() - 1;
                }
            }
        }

        room.getCellOccupied().remove(vector2D);
        setVector2D(new Vector2D(vector2D.getX(), y1));
        room.addCellOccupied(vector2D);

        return Boolean.TRUE;
    }

    @Override
    public Boolean moveSouth(Room room, Command command) {
        Integer y1 = vector2D.getY() - command.getNumberOfSteps();
        Integer y2 = null;

        for(int j=0; j<room.getCellOccupied().size(); j++){
            if(room.getCellOccupied().get(j).getY() < vector2D.getY() && room.getCellOccupied().get(j).getX().equals(vector2D.getX())){
                y2 = room.getCellOccupied().get(j).getY();
                break;
            }
        }
        if(y1 < 0) y1 = 0;
        if(room.getObstacles().size() == 0){
            if(y2 != null && y1 <= y2) y1 = y2 + 1;
        }

        for(int i=0; i<room.getObstacles().size(); i++){
            if(room.getObstacles().get(i).getStart().getY().equals(room.getObstacles().get(i).getEnd().getY())){
                if(y2 != null && y1 <= y2 && y2 > room.getObstacles().get(i).getStart().getY())
                    y1 = y2 + 1;
                else if(vector2D.getX() >= room.getObstacles().get(i).getStart().getX() && vector2D.getX() < room.getObstacles().get(i).getEnd().getX()){
                    if(y1 <= room.getObstacles().get(i).getStart().getY() && vector2D.getY() >= room.getObstacles().get(i).getStart().getY())
                        y1 = room.getObstacles().get(i).getStart().getY() + 1;
                }
            }
        }

        room.getCellOccupied().remove(vector2D);
        setVector2D(new Vector2D(vector2D.getX(), y1));
        room.addCellOccupied(vector2D);

        return Boolean.TRUE;
    }

    @Override
    public Boolean moveWest(Room room, Command command) {
        Integer x1 = vector2D.getX() - command.getNumberOfSteps();
        Integer x2 = null;

        for(int j = 0; j < room.getCellOccupied().size(); j++){
            if(room.getCellOccupied().get(j).getX() < vector2D.getX() && room.getCellOccupied().get(j).getY().equals(vector2D.getY())){
                x2 = room.getCellOccupied().get(j).getX();
                break;
            }
        }
        if(x1 < 0) x1 = 0;
        if(room.getObstacles().size() == 0){
            if(x2 != null && x1 <= x2) x1 = x2 + 1;
        }

        for(int i=0; i<room.getObstacles().size(); i++){
            if(room.getObstacles().get(i).getStart().getX().equals(room.getObstacles().get(i).getEnd().getX())){
                if(x2 != null && x1 <= x2 && x2 > room.getObstacles().get(i).getStart().getX()){
                    x1 = x2 + 1;
                }
                else if(vector2D.getY() >= room.getObstacles().get(i).getStart().getY() && vector2D.getY() < room.getObstacles().get(i).getEnd().getY()){
                    if(x1 <= room.getObstacles().get(i).getStart().getX() && vector2D.getX() >= room.getObstacles().get(i).getStart().getX())
                        x1 = room.getObstacles().get(i).getStart().getX() + 1;
                }
            }
        }

        room.getCellOccupied().remove(vector2D);
        setVector2D(new Vector2D(x1, vector2D.getY()));
        room.addCellOccupied(vector2D);

        return Boolean.TRUE;
    }
}
