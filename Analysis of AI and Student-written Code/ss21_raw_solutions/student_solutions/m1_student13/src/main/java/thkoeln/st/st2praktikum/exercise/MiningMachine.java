package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiningMachine implements Executable {
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


    public String currentPosition(){
        return "("+ this.room.getXPosition() +","+this.room.getYPosition() + ")";
    }


    public boolean execute(String commandString, HashMap<UUID,Field>fieldHashMap, HashMap<UUID,Connection>connectionHashMap){
        String [] coordinates = determineCoordinates(commandString);
        String direction = coordinates[0];
        boolean output;

        if(!(direction.equals("en") || direction.equals("tr"))){
            int numberOfSteps = Integer.parseInt(coordinates[1]);
            output = this.move(direction,numberOfSteps);
        }else {
            UUID fieldID = UUID.fromString(coordinates[1]);
            Field field = fieldHashMap.get(fieldID);
            if(direction.equals("en")){
                output = this.placeOnField(field);
            }else{
                output = this.transport(field, connectionHashMap);
            }
        }
        return output;
    }


    private boolean move(String command, int steps){

        switch(command){
            case "no": this.room = this.room.moveNorth(steps);
                return true;
            case "so": this.room = this.room.moveSouth(steps);
                return true;
            case "ea": this.room = this.room.moveEast(steps);
                return true;
            case "we": this.room = this.room.moveWest(steps);
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

    private boolean transport(Field field, HashMap<UUID,Connection>connectionHashMap) {

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
