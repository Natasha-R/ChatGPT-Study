package thkoeln.st.st2praktikum.exercise.room.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.addStuff;

import java.util.ArrayList;
import java.util.List;

public class RoomList implements addStuff {
    private List<Room> roomList = new ArrayList<Room>();




    @Override
    public void add(Object toAdd) {
        roomList.add((Room) toAdd);

    }

    public List<Room> getRoomList() {
        return roomList;
    }
}
