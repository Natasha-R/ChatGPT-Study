package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Robot {

    int[] coordinates = new int[2];
    Room room;
    String name;

    UUID id = UUID.randomUUID();


    public Robot(String givenName){
        name = givenName;
    }
    public UUID getId(){
        return id;
    }public Room getRoom() {
        return room;
    }public int[] getCoordinates() {
        return coordinates;
    }

    public void move(int[] givenCoordinates){
        coordinates = givenCoordinates;
    }

    public void setRoom(Room givenRoom) {
        room = givenRoom;
    }
}
