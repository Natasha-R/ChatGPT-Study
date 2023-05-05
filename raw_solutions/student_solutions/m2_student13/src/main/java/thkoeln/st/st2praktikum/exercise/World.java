package thkoeln.st.st2praktikum.exercise;

public class World {

    public Connection createConnection(Field sourceField, Point sourcePoint, Field destinationField, Point destinationPoint){

        Room entryRoom = sourceField.getRoom(sourcePoint.getX(),sourcePoint.getY());
        Room exitRoom = destinationField.getRoom(destinationPoint.getX(),destinationPoint.getY());

        return new Connection(entryRoom,exitRoom);

    }


}
