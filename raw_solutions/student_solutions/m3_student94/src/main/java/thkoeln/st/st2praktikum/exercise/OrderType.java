package thkoeln.st.st2praktikum.exercise;

public enum OrderType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER,
    illegal;
    public boolean isOrderType(){
        return (this==NORTH||this==WEST||this==SOUTH||this==EAST);
    }
}
