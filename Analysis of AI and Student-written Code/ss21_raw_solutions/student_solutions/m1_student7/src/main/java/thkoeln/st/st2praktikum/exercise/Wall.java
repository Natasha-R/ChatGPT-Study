package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Wall {
    int[] startPoint = new int[2];
    int[] endPoint = new int[2];
    UUID id = UUID.randomUUID();

    public Wall(String wallString){
        String[] splitWallString = wallString.split("[-(),]");
        startPoint[0] = Integer.parseInt(splitWallString[1]);
        startPoint[1] = Integer.parseInt(splitWallString[2]);
        endPoint[0] = Integer.parseInt(splitWallString[5]);
        endPoint[1] = Integer.parseInt(splitWallString[6]);
    }

    public int[] getStartPoint() {
        return startPoint;
    }public int[] getEndPoint() {
        return endPoint;
    }public UUID getId(){
        return id;
    }
}
