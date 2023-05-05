package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class TransportFields {

    Integer x;
    Integer y;
    UUID transportFieldRoomId;
    Integer destinationX;
    Integer destinationY;
    UUID destinationTransportFieldRoomId;

    public TransportFields(Integer x, Integer y, UUID transportFieldRoomId,Integer destinationX, Integer destinationY, UUID destinationTransportFieldRoomId){
        this.x=x;
        this.y=y;
        this.transportFieldRoomId=transportFieldRoomId;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationTransportFieldRoomId = destinationTransportFieldRoomId;
    }


}
