package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class TidyUpRobot implements Robot, RoomInitializable, Transportable, EastMoveable, NorthMoveable, WestMovable, SouthMovable {
    private UUID robotId;
    private String name;
    private Pair<Integer,Integer> coordinate;
    private UUID robotRoomId;
    private Location location;

    public TidyUpRobot(String name) {
        this.name = name;
        this.robotId = UUID.randomUUID();
        this.location = new Location();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getRobotId() {
        return robotId;
    }

    @Override
    public UUID getRobotRoomId(){
        this.robotRoomId = location.getRobotNewRoomId();
        return robotRoomId;
    }

    @Override
    public Pair<Integer,Integer> getCoordinate() {
        coordinate = location.getCoordinate();
        return this.coordinate;
    }

    @Override
    public Boolean roomInitialize(Room room, String command) {
        if (room.getCell()[0][0].getCellStateisFree() == Boolean.TRUE) {
            location.setNewRoom(room.getRoomId());
            room.getCell()[0][0].setCellStateIsFree(false);

            if (coordinate == null) {
                location.setOldCoordinate(null);
                location.setNewCoordinate(new Pair<>(0,0));
                return Boolean.TRUE;
            } else {
                Pair<Integer, Integer> oldLocation = new Pair<>(coordinate.getFirst(), coordinate.getSecond());
                location.setOldCoordinate(oldLocation);
                location.setNewCoordinate(new Pair<>(0,0));
                return Boolean.TRUE;
            }
        } else return Boolean.FALSE;
    }

    @Override
    public Boolean transportTo(Room currentRoom, Room destinationRoom, String command) {
        Integer x = Integer.parseInt(currentRoom.getConnection(destinationRoom.getRoomId()).getDestinationCoordinate().replace("(","").replaceAll(",.*",""));
        Integer y = Integer.parseInt(currentRoom.getConnection(destinationRoom.getRoomId()).getDestinationCoordinate().replaceAll(".*,","").replace(")",""));

        currentRoom.getCell()[coordinate.getFirst()][coordinate.getSecond()].setCellStateIsFree(true);
        if(destinationRoom.getCell()[x][y].getCellStateisFree() == Boolean.TRUE) {
            location.setNewRoom(destinationRoom.getRoomId());
            location.setNewCoordinate(new Pair<>(x, y));
            destinationRoom.getCell()[x][y].setCellStateIsFree(false);
            return Boolean.TRUE;
        }

        currentRoom.getCell()[coordinate.getFirst()][coordinate.getSecond()].setCellStateIsFree(false);
        return Boolean.FALSE;
    }

    @Override
    public Boolean moveEast(Room room, String command) {
        Integer move = Integer.parseInt(command.replaceAll("[^0-9]",""));

        int x1 = getCoordinate().getFirst() + move;
        Integer x2 = null; //for check, whether there is another robot or not

        for (int j = coordinate.getFirst() + 1; j < room.getWidth(); j++) {
            if (!room.getCell()[j][coordinate.getSecond()].getCellStateisFree()) {
                x2 = j;
                break;
            }
        }
        if(x1 >= room.getWidth()) x1 = room.getWidth() - 1;
        if(room.getObstacleStrings().size() == 0) {
            if (x2 != null && x1 >= x2) x1 = x2 - 1;
        }

        for (int i=0; i< room.getObstacleStrings().size(); i++){
            if (room.getObstacleStartCoordinate().get(i).getFirst().equals(room.getObstacleEndCoordinate().get(i).getFirst())){
                if(x2 != null && x1 >=x2 && x2 < room.getObstacleStartCoordinate().get(i).getFirst())
                    x1 = x2 - 1;
                else if (coordinate.getSecond() >= room.getObstacleStartCoordinate().get(i).getSecond() && coordinate.getSecond() < room.getObstacleEndCoordinate().get(i).getSecond()){
                    if (x1 >= room.getObstacleStartCoordinate().get(i).getFirst()) {
                        x1 = room.getObstacleStartCoordinate().get(i).getFirst() - 1;
                    }
                }
            }
        }
        location.setOldCoordinate(coordinate);
        room.getCell()[coordinate.getFirst()][coordinate.getSecond()].setCellStateIsFree(true);
        location.setNewCoordinate(new Pair<>(x1, coordinate.getSecond()));
        room.getCell()[x1][coordinate.getSecond()].setCellStateIsFree(false);

        return Boolean.TRUE;
    }

    @Override
    public Boolean moveNorth(Room room, String command) {
        Integer move = Integer.parseInt(command.replaceAll("[^0-9]",""));

        int y1 = getCoordinate().getSecond() + move;
        Integer y2 = null;
        for (int j = coordinate.getSecond()+1; j < room.getHeight(); j++) {
            if (!room.getCell()[coordinate.getFirst()][j].getCellStateisFree()) {
                y2 = j;
                break;
            }
        }
        if(y1 >= room.getHeight()) y1 = room.getHeight() - 1;
        if(room.getObstacleStrings().size() == 0) {
            if (y2 != null && y1 >= y2) y1 = y2 - 1;
        }

        for (int i=0; i< room.getObstacleStrings().size(); i++){
            if (room.getObstacleStartCoordinate().get(i).getSecond().equals(room.getObstacleEndCoordinate().get(i).getSecond())){
                if(y2 != null && y1 >= y2 && y2 < room.getObstacleStartCoordinate().get(i).getSecond())
                    y1 = y2 -1;
                else if (coordinate.getFirst() >= room.getObstacleStartCoordinate().get(i).getFirst() && coordinate.getFirst() < room.getObstacleEndCoordinate().get(i).getFirst()){
                    if (y1 >= room.getObstacleStartCoordinate().get(i).getSecond()) {
                        y1 = room.getObstacleStartCoordinate().get(i).getSecond() - 1;
                    }
                }
            }
        }
        location.setOldCoordinate(coordinate);
        room.getCell()[coordinate.getFirst()][coordinate.getSecond()].setCellStateIsFree(true);
        location.setNewCoordinate(new Pair<>(coordinate.getFirst(), y1));
        room.getCell()[coordinate.getFirst()][y1].setCellStateIsFree(false);

        return Boolean.TRUE;
    }

    @Override
    public Boolean moveSouth(Room room, String command) {
        Integer move = Integer.parseInt(command.replaceAll("[^0-9]",""));

        int y1 = getCoordinate().getSecond() - move;
        Integer y2 = null;
        for (int j = coordinate.getSecond()-1; j >= 0; j--) {
            if (!room.getCell()[coordinate.getFirst()][j].getCellStateisFree()) {
                y2 = j;
                break;
            }
        }
        if(y1 < 0) y1 = 0;
        if(room.getObstacleStrings().size() == 0) {
            if (y2 != null && y1 <= y2) y1 = y2 + 1;
        }

        for (int i=0; i< room.getObstacleStrings().size(); i++){
            if (room.getObstacleStartCoordinate().get(i).getSecond().equals(room.getObstacleEndCoordinate().get(i).getSecond())){
                if (coordinate.getFirst() >= room.getObstacleStartCoordinate().get(i).getFirst() && coordinate.getFirst() < room.getObstacleEndCoordinate().get(i).getFirst()){
                    if(y2 != null && y1 <= y2 && y2 > room.getObstacleStartCoordinate().get(i).getSecond())
                        y1 = y2 + 1;
                    else if (y1 <= room.getObstacleStartCoordinate().get(i).getSecond()) {
                        y1 = room.getObstacleStartCoordinate().get(i).getSecond() + 1;
                    }
                }
            }
        }
        location.setOldCoordinate(coordinate);
        room.getCell()[coordinate.getFirst()][coordinate.getSecond()].setCellStateIsFree(true);
        location.setNewCoordinate(new Pair<>(coordinate.getFirst(), y1));
        room.getCell()[coordinate.getFirst()][y1].setCellStateIsFree(false);

        return Boolean.TRUE;
    }

    @Override
    public Boolean moveWest(Room room, String command) {
        Integer move = Integer.parseInt(command.replaceAll("[^0-9]",""));

        int x1 = getCoordinate().getFirst() - move;
        Integer x2 = null;

        for (int j = coordinate.getFirst() + 1; j < room.getWidth(); j++) {
            if (!room.getCell()[j][coordinate.getSecond()].getCellStateisFree()) {
                x2 = j;
                break;
            }
        }
        if(x1 < 0) x1 = 0;
        if(room.getObstacleStrings().size() == 0) {
            if (x2 != null && x1 <= x2) x1 = x2 + 1;
        }

        for (int i=0; i< room.getObstacleStrings().size(); i++){
            if (room.getObstacleStartCoordinate().get(i).getFirst().equals(room.getObstacleEndCoordinate().get(i).getFirst())){
                if (coordinate.getSecond() >= room.getObstacleStartCoordinate().get(i).getSecond() && coordinate.getSecond() < room.getObstacleEndCoordinate().get(i).getSecond()){
                    if(x2 != null && x1 <= x2 && x2 > room.getObstacleStartCoordinate().get(i).getFirst())
                        x1 = x2 + 1;
                    else if (x1 <= room.getObstacleStartCoordinate().get(i).getFirst()) {
                        x1 = room.getObstacleStartCoordinate().get(i).getFirst() + 1;
                    }
                }
            }
        }
        location.setOldCoordinate(coordinate);
        room.getCell()[coordinate.getFirst()][coordinate.getSecond()].setCellStateIsFree(true);
        location.setNewCoordinate(new Pair<>(x1, coordinate.getSecond()));
        room.getCell()[x1][coordinate.getSecond()].setCellStateIsFree(false);

        return Boolean.TRUE;
    }
}
