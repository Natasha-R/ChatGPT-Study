package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

 class BarrierInRoom implements IdReturnable_Interface {

    String solvevBarrierString;
    private UUID roomIdFromBArrier;

    public BarrierInRoom(String solvedBarrierString, UUID roomIdFromBArrier){
        this.roomIdFromBArrier=roomIdFromBArrier;
        this.solvevBarrierString=solvedBarrierString;
    }


     @Override
     public UUID returnUUID() {
         return roomIdFromBArrier;
     }
 }
