package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class TidyUpRobot implements IdReturnableInterface {

    private UUID uuidRobot;
    private String robotName;
    private UUID roomUUID;

    public TidyUpRobot(String robotName){
        this.robotName = robotName;
        this.uuidRobot = UUID.randomUUID();
    }

    @Override
    public UUID returnUUID() {
        return uuidRobot;
    }

    public UUID getRoomUUID(){
        return roomUUID;
    }
    public void setRoomUUID(UUID id){
        roomUUID=id;
    }



}
