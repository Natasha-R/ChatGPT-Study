package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomRepository {


    public Room getRoom(UUID roomID){
        for ( int i = 0 ; i < rooms.size() ; i++ ){
            if (rooms.get(i).getRoomID().equals(roomID)){
                return rooms.get(i);
            }
        }
        throw new UnsupportedOperationException("There is no Room with this RoomID");
    }

    private List<Room> rooms = new ArrayList<>();

    public void setRooms(List<Room> rooms){
        this.rooms = rooms;
    }

    public List<Room> getRooms(){
        return rooms;
    }

}
