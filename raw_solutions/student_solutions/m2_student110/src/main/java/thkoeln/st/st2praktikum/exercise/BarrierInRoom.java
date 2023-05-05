package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class BarrierInRoom implements IdReturnableInterface {
    private String solvevBarrierString;
    private UUID roomIdFromBArrier;

    public BarrierInRoom(String solvedBarrierString, UUID roomIdFromBArrier){
        this.roomIdFromBArrier=roomIdFromBArrier;
        this.solvevBarrierString=solvedBarrierString;
    }

    @Override
    public UUID returnUUID() {
        return roomIdFromBArrier;
    }

    public String getSolvevBarrierString(){
        return solvevBarrierString;
    }
    public void setSolvevBarrierString(String solvevBarrierString){
        solvevBarrierString=solvevBarrierString;
    }
}
