package thkoeln.st.st2praktikum.exercise;


import org.springframework.beans.factory.annotation.Autowired;

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







