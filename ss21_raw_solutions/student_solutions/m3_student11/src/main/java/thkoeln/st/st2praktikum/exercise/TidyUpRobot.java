package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;

import javax.persistence.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class TidyUpRobot {

    @Embedded
    @Getter
    @Setter
    private Coordinate coordinate;

    @Getter
    private String name;

    @ManyToOne
    @Getter
    @Setter
    private Room room;

    @Id
    @Getter
    private final UUID ID = UUID.randomUUID();

    //@Transient
    //private TidyUpRobotService world;

    public TidyUpRobot(){}

    public TidyUpRobot(String name){
        this.name = name;
        this.coordinate = new Coordinate(0,0);
    }


    public UUID getRoomId(){
        if(room != null)
            return this.getRoom().getID();
        else return null;
    }

    public void setCoordinates(int x, int y){
        this.coordinate = new Coordinate(x, y);
    }

    public void setCoordinates(String Coordinates){
        Coordinates = Coordinates.replace("(","").replace(")","");
        String[] cords = Coordinates.split(",");
        setCoordinates(Integer.parseInt(cords[0]), Integer.parseInt(cords[1]));
    }

    public String getCoordinateString(){
        return "(" + coordinate.getX() + "," + coordinate.getY() + ")";
    }


    public boolean transport(Coordinate coordinate, Room dest, TidyUpRobotRepository tidyUpRobotRepository){
        if(this.checkForBlockingRobot(coordinate, null, dest.getID(), tidyUpRobotRepository ))
            return false;

        setRoom(dest);
        setCoordinate(coordinate);
        return true;
    }

    public boolean checkForBlockingRobot(Coordinate coordinate, @Nullable DirectionsType direction, UUID roomId, TidyUpRobotRepository tidyUpRobotRepository){
        int x = coordinate.getX();
        int y = coordinate.getY();

        if(direction != null){
            switch(direction){
                case NORTH: y++; break;
                case SOUTH: y--; break;
                case EAST: x++; break;
                case WEST: x--; break;
            }
        }
        if(x < 0 || y < 0) return false;

        for (TidyUpRobot tidyUpRobot : tidyUpRobotRepository.findAll()) {
            if(tidyUpRobot.getRoomId() != null) {
                if (tidyUpRobot.coordinate.equals(new Coordinate(x, y)) && !tidyUpRobot.ID.equals(this.ID) && roomId.equals(tidyUpRobot.getRoomId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean noBlockage(Coordinate coordinate, DirectionsType direction, UUID roomId, RoomRepository roomRepository, TidyUpRobotRepository tidyUpRobotRepository){

        Room room = roomRepository.findById(roomId).get();

        if(room.checkForBlockingObstacle(coordinate, direction)) return false;

        return !checkForBlockingRobot(coordinate, direction, roomId, tidyUpRobotRepository);
    }

    //move tidyUpRobot stepwise, checking for obstructions with each step
    public void move(DirectionsType direction, int steps, TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository){
        if(this.room == null){ return; }

        //Room room = world.getRoomFromUUID(this.roomId);

        for(int i = 0; i< steps; i++) {
            int robotX = this.coordinate.getX();
            int robotY = this.coordinate.getY();

            if (noBlockage(this.coordinate, direction, room.getID(), roomRepository, tidyUpRobotRepository)){
                switch (direction) {
                    case NORTH:
                        if(room.inBounds(robotX, robotY + 1))
                            this.goNorth();
                        break;
                    case EAST:
                        if(room.inBounds(robotX + 1, robotY))
                            this.goEast();
                        break;
                    case SOUTH:
                        if (room.inBounds(robotX, robotY - 1))
                            this.goSouth();
                        break;
                    case WEST:
                        if (room.inBounds(robotX - 1, robotY))
                            this.goWest();
                        break;
                }
            }
        }

    }

    public boolean spawn(Room room, TidyUpRobotRepository tidyUpRobotRepository){
        if(this.room == null){
            if(this.checkForBlockingRobot(new Coordinate(0,0), null, room.getID(), tidyUpRobotRepository))
                return false;
            this.setRoom(room);
            return true;
        }
        return false;
    }

    public boolean transport(UUID destUUID, ConnectionRepository connectionRepository, TidyUpRobotRepository tidyUpRobotRepository){

        for (Connection connection : connectionRepository.findAll()) {
            UUID dest = connection.getDest().getID();
            UUID source = connection.getSource().getID();

            if (dest.equals(destUUID) && source.equals(this.room.getID()) && connection.getSourceCords().equals(this.coordinate)){
                return this.transport(connection.getDestCords(), connection.getDest(), tidyUpRobotRepository);
            }
        }
        return false;
    }


    private void goNorth(){
        this.coordinate = new Coordinate(this.coordinate.getX(), this.coordinate.getY() + 1);
    }

    private void goSouth(){
        this.coordinate = new Coordinate(this.coordinate.getX(), this.coordinate.getY() - 1);
    }

    private void goEast(){
        this.coordinate = new Coordinate(this.coordinate.getX() + 1, this.coordinate.getY());
    }

    private void goWest(){
        this.coordinate = new Coordinate(this.coordinate.getX() - 1, this.coordinate.getY());
    }

}
