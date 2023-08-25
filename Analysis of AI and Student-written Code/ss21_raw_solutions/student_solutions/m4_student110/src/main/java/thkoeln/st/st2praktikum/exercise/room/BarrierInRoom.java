package thkoeln.st.st2praktikum.exercise.room;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class BarrierInRoom   {
    @Id
    @Getter
    private UUID barrierInRoomId = UUID.randomUUID();

    @Getter
    @Setter
    private String solvedBarrierString;

    @Getter
    @Setter
    private UUID roomIdFromBarrier;

    public BarrierInRoom(){}

    public BarrierInRoom(String solvedBarrierString, UUID roomIdFromBArrier){
        this.roomIdFromBarrier=roomIdFromBArrier;
        this.solvedBarrierString=solvedBarrierString;
    }

}