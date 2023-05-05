package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements ConnectionInterface {

    UUID id =UUID.randomUUID();

    UUID sourceFieldId;
    Coordinate sourceCoordinate;
    UUID destinationFieldId;
    Coordinate destinationCoordinate;

    public Connection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate){
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = stringToCoordinate(sourceCoordinate);
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = stringToCoordinate(destinationCoordinate);
    }

    public UUID getId(){
        return id;
    }
    public Connection getConnection(){
        return this;
    }

    public Coordinate stringToCoordinate(String coord){
        String[] stringArray = coord.split("[\\W]");
        int[] intArray = new int[2];
        int act = 0;
        for (int i = 0; i < stringArray.length; i++){
            if(!stringArray[i].isEmpty()) {
                intArray[act] = Integer.parseInt(stringArray[i]);
                act++;
            }
        }
        return new Coordinate(intArray[0], intArray[1]);
    }
}
