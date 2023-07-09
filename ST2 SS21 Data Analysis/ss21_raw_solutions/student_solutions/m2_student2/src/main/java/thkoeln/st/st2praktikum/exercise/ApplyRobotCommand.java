package thkoeln.st.st2praktikum.exercise;

public class ApplyRobotCommand implements RoomInitializable, Transportable, EastMovable, NorthMovable, SouthMovable, WestMovable {

    @Override
    public Boolean roomInitialize(TidyUpRobot tidyUpRobot, Room room, Command command) {
        if(room.getCell()[0][0].getCellStateIsFree() == Boolean.TRUE){
            tidyUpRobot.getLocation().setNewRoom(room.getRoomId());
            room.getCell()[0][0].setCellStateIsFree(false);

            if(tidyUpRobot.getCoordinate() == null){
                tidyUpRobot.getLocation().setOldCoordinate(null);
                tidyUpRobot.getLocation().setNewCoordinate(new Vector2D(0,0));
                return Boolean.TRUE;
            }
            else{
                tidyUpRobot.getLocation().setOldCoordinate(tidyUpRobot.getCoordinate());
                tidyUpRobot.getLocation().setNewCoordinate(new Vector2D(0,0));
                return Boolean.TRUE;
            }

        } else return Boolean.FALSE;
    }

    @Override
    public Boolean transportTo(TidyUpRobot tidyUpRobot, Room currentRoom, Room destinationRoom, Command command) {
        Integer x = currentRoom.getConnection(destinationRoom.getRoomId()).getDestinationCoordinate().getX();
        Integer y = currentRoom.getConnection(destinationRoom.getRoomId()).getDestinationCoordinate().getY();

        currentRoom.getCell()[tidyUpRobot.getCoordinate().getX()][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(true);
        if(destinationRoom.getCell()[x][y].getCellStateIsFree() == Boolean.TRUE){
            tidyUpRobot.getLocation().setNewRoom(destinationRoom.getRoomId());
            tidyUpRobot.getLocation().setNewCoordinate(new Vector2D(x,y));
            destinationRoom.getCell()[x][y].setCellStateIsFree(false);
            return Boolean.TRUE;
        }

        currentRoom.getCell()[tidyUpRobot.getCoordinate().getX()][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(false);
        return Boolean.FALSE;
    }

    @Override
    public Boolean moveEast(TidyUpRobot tidyUpRobot, Room room, Command command) {
        Integer x1 = tidyUpRobot.getCoordinate().getX() + command.getNumberOfSteps();
        Integer x2 = null; //indicate, whether there is another robot or not

        for(int j = tidyUpRobot.getCoordinate().getX() + 1; j < room.getWidth(); j++){
            if(!room.getCell()[j][tidyUpRobot.getCoordinate().getY()].getCellStateIsFree()){
                x2 = j;
                break;
            }
        }
        if(x1 >= room.getWidth()) x1 = room.getWidth() - 1;
        if(room.getObstacles().size() == 0){
            if(x2 != null && x1 >= x2) x1 = x2 - 1;
        }

        for(int i=0; i<room.getObstacles().size(); i++){
            if(room.getObstacles().get(i).getStart().getX().equals(room.getObstacles().get(i).getEnd().getX())){
                if(x2 != null && x1 >= x2 && x2 < room.getObstacles().get(i).getStart().getX())
                    x1 = x2 - 1;
                else if(tidyUpRobot.getCoordinate().getY() >= room.getObstacles().get(i).getStart().getY() && tidyUpRobot.getCoordinate().getY() < room.getObstacles().get(i).getEnd().getY()){
                    if(x1 >= room.getObstacles().get(i).getStart().getX() && tidyUpRobot.getCoordinate().getX() < room.getObstacles().get(i).getStart().getX())
                        x1 = room.getObstacles().get(i).getStart().getX() - 1;
                }
            }
        }

        tidyUpRobot.getLocation().setOldCoordinate(tidyUpRobot.getCoordinate());
        room.getCell()[tidyUpRobot.getCoordinate().getX()][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(true);
        tidyUpRobot.getLocation().setNewCoordinate(new Vector2D(x1, tidyUpRobot.getCoordinate().getY()));
        room.getCell()[x1][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(false);

        return Boolean.TRUE;
    }

    @Override
    public Boolean moveNorth(TidyUpRobot tidyUpRobot, Room room, Command command) {
        Integer y1 = tidyUpRobot.getCoordinate().getY() + command.getNumberOfSteps();
        Integer y2 = null;

        for(int j = tidyUpRobot.getCoordinate().getY() + 1; j < room.getHeight(); j++){
            if(!room.getCell()[tidyUpRobot.getCoordinate().getX()][j].getCellStateIsFree()){
                y2 = j;
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
                else if(tidyUpRobot.getCoordinate().getX() >= room.getObstacles().get(i).getStart().getX() && tidyUpRobot.getCoordinate().getX() < room.getObstacles().get(i).getEnd().getX()){
                    if(y1 >= room.getObstacles().get(i).getStart().getY() && tidyUpRobot.getCoordinate().getY() < room.getObstacles().get(i).getStart().getY())
                        y1 = room.getObstacles().get(i).getStart().getY() - 1;
                }
            }
        }

        tidyUpRobot.getLocation().setOldCoordinate(tidyUpRobot.getCoordinate());
        room.getCell()[tidyUpRobot.getCoordinate().getX()][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(true);
        tidyUpRobot.getLocation().setNewCoordinate(new Vector2D(tidyUpRobot.getCoordinate().getX(), y1));
        room.getCell()[tidyUpRobot.getCoordinate().getX()][y1].setCellStateIsFree(false);

        return Boolean.TRUE;
    }

    @Override
    public Boolean moveSouth(TidyUpRobot tidyUpRobot, Room room, Command command) {
        Integer y1 = tidyUpRobot.getCoordinate().getY() - command.getNumberOfSteps();
        Integer y2 = null;

        for(int j = tidyUpRobot.getCoordinate().getY() - 1; j >= 0; j--){
            if(!room.getCell()[tidyUpRobot.getCoordinate().getX()][j].getCellStateIsFree()){
                y2 = j;
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
                else if(tidyUpRobot.getCoordinate().getX() >= room.getObstacles().get(i).getStart().getX() && tidyUpRobot.getCoordinate().getX() < room.getObstacles().get(i).getEnd().getX()){
                    if(y1 <= room.getObstacles().get(i).getStart().getY() && tidyUpRobot.getCoordinate().getY() >= room.getObstacles().get(i).getStart().getY())
                        y1 = room.getObstacles().get(i).getStart().getY() + 1;
                }
            }
        }

        tidyUpRobot.getLocation().setOldCoordinate(tidyUpRobot.getCoordinate());
        room.getCell()[tidyUpRobot.getCoordinate().getX()][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(true);
        tidyUpRobot.getLocation().setNewCoordinate(new Vector2D(tidyUpRobot.getCoordinate().getX(), y1));
        room.getCell()[tidyUpRobot.getCoordinate().getX()][y1].setCellStateIsFree(false);
        return Boolean.TRUE;
    }

    @Override
    public Boolean moveWest(TidyUpRobot tidyUpRobot, Room room, Command command) {
        Integer x1 = tidyUpRobot.getCoordinate().getX() - command.getNumberOfSteps();
        Integer x2 = null;

        for(int j = tidyUpRobot.getCoordinate().getX() - 1; j >= 0; j--){
            if(!room.getCell()[j][tidyUpRobot.getCoordinate().getY()].getCellStateIsFree()){
                x2 = j;
                break;
            }
        }
        if(x1 < 0) x1 = 0;
        if(room.getObstacles().size() == 0){
            if(x2 != null && x1 <= x2) x1 = x2 + 1;
        }

        for(int i=0; i<room.getObstacles().size(); i++){
            if(room.getObstacles().get(i).getStart().getX().equals(room.getObstacles().get(i).getEnd().getX())){
                if(x2 != null && x1 <= x2 && x2 > room.getObstacles().get(i).getStart().getX())
                    x1 = x2 + 1;
                else if(tidyUpRobot.getCoordinate().getY() >= room.getObstacles().get(i).getStart().getY() && tidyUpRobot.getCoordinate().getY() < room.getObstacles().get(i).getEnd().getY()){
                    if(x1 <= room.getObstacles().get(i).getStart().getX() && tidyUpRobot.getCoordinate().getX() >= room.getObstacles().get(i).getStart().getX())
                        x1 = room.getObstacles().get(i).getStart().getX() + 1;
                }
            }
        }

        tidyUpRobot.getLocation().setOldCoordinate(tidyUpRobot.getCoordinate());
        room.getCell()[tidyUpRobot.getCoordinate().getX()][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(true);
        tidyUpRobot.getLocation().setNewCoordinate(new Vector2D(x1, tidyUpRobot.getCoordinate().getY()));
        room.getCell()[x1][tidyUpRobot.getCoordinate().getY()].setCellStateIsFree(false);

        return Boolean.TRUE;
    }
}
