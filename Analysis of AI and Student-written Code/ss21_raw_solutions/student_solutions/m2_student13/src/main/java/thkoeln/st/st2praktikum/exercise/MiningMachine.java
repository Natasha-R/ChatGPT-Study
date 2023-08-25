package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiningMachine{
    private String name;
    private UUID uuid;
    private Room room;

    public MiningMachine(String name){
        this.name = name;
        uuid = UUID.randomUUID();
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public UUID getUuid() {
        return uuid;
    }


    private String [] determineCoordinates(String instruction){
        String [] coordinates = instruction
                .substring(1,instruction.length()-1)
                .split(",");

        return coordinates;
    }


    public Point currentPosition(){
        //return new Point(this.room.getXPosition(), this.room.getYPosition());
        return new Point(this.room.getPoint().getX(),this.room.getPoint().getY());
    }


    public boolean execute(Task task, HashMap<UUID,Field>fieldHashMap, HashMap<UUID,Connection>connectionHashMap){
        boolean output;

        if(task.getTaskType() != TaskType.ENTER && task.getTaskType() != TaskType.TRANSPORT){
            output = this.move(task.getTaskType(),task.getNumberOfSteps());
        }else {
            UUID fieldID = task.getGridId();
            Field field = fieldHashMap.get(fieldID);
            if(task.getTaskType() == TaskType.ENTER){
                output = this.placeOnField(field);
            }else{
                output = this.transport(field, connectionHashMap);
            }
        }
        return output;
    }


    private boolean move(TaskType type, int steps){

        switch(type){
            case NORTH: this.room = this.room.moveNorth(steps);
                this.room.setBlocked(true);
                return true;
            case SOUTH: this.room = this.room.moveSouth(steps);
                this.room.setBlocked(true);
                return true;
            case EAST: this.room = this.room.moveEast(steps);
                this.room.setBlocked(true);
                return true;
            case WEST: this.room = this.room.moveWest(steps);
                this.room.setBlocked(true);
                return true;
        }
        return false;
    }


    private boolean placeOnField(Field field){

        if(!field.getFieldWithRooms()[0][0].isBlocked()){
            this.room = field.getFieldWithRooms()[0][0];
            this.room.setBlocked(true);
            return true;
        }else {
            return false;
        }
    }

    private boolean transport(Field field, HashMap<UUID,Connection> connectionHashMap) {

        for (Map.Entry<UUID, Connection> entry : connectionHashMap.entrySet()) {

            //mit == überprüfen, ob beide den selben Raum referenzieren
            if (this.room == entry.getValue().getEntryRoom()) {
                if (field.getUuid().equals(entry.getValue().getExitRoom().getField().getUuid())) {
                    this.room = entry.getValue().getExitRoom();
                    return true;
                }
            }
        }
        return false;
    }


}