package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public class Room extends TidyUpRobotService implements IdReturnableInterface {

    private UUID uuidRoom;
    private Integer height;
    private Integer width;

    public Room(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.uuidRoom = UUID.randomUUID();
    }

    public Room(UUID uuidRoom){
        this.uuidRoom = uuidRoom;
        this.height=getHeight();
        this.width=getWidth();
    }

    @Override
    public UUID returnUUID() {
        return uuidRoom;
    }
    public Integer getHeight(){ return height; }
    public Integer getWidth(){
        return width;
    }

    public Integer checkRoomBoundariesX(List<Room> allRooms){
        UUID roomId = this.uuidRoom;
        for (int i =0; i<allRooms.size(); i++){
            if(allRooms.get(i).uuidRoom.equals(roomId)){
                return allRooms.get(i).getWidth();
            }
        }
        return null;
    }

    public Integer checkRoomBoundariesY(List<Room> allRooms){
        UUID roomId = this.uuidRoom;
        for (int i =0; i<allRooms.size(); i++){
            if(allRooms.get(i).uuidRoom.equals(roomId)){
                return allRooms.get(i).getHeight();
            }
        }
        return null;
    }


}
