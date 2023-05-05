package thkoeln.st.st2praktikum.exercise;

public class RoomFactory {
    public Room getRoom(int height, int width) {
        return new Room(height, width);
    }
}
