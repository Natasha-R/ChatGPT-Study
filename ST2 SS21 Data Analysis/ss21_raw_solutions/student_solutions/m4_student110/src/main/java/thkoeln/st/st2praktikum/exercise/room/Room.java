package thkoeln.st.st2praktikum.exercise.room;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Entity
public class Room   {

    @Id
    @Getter
    private UUID roomId = UUID.randomUUID();

    @Getter
    @Setter
    private Integer height;

    @Getter
    @Setter
    private Integer width;


    public Room(){}

    public Room(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.roomId = UUID.randomUUID();
    }

    public Integer checkRoomBoundariesX(List<Room> allRooms){
        UUID roomId = this.roomId;
        for (int i =0; i<allRooms.size(); i++){
            if(allRooms.get(i).roomId.equals(roomId)){
                return allRooms.get(i).getWidth();
            }
        }
        return null;
    }

    public Integer checkRoomBoundariesY(List<Room> allRooms){
        UUID roomId = this.roomId;
        for (int i =0; i<allRooms.size(); i++){
            if(allRooms.get(i).roomId.equals(roomId)){
                return allRooms.get(i).getHeight();
            }
        }
        return null;
    }





}