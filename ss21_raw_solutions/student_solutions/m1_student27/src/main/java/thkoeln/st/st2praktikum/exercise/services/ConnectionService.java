package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.entyties.Connection;
import thkoeln.st.st2praktikum.exercise.entyties.Point;
import thkoeln.st.st2praktikum.exercise.repository.ConnectionRepository;

import java.util.UUID;

public class ConnectionService implements ConnectionRepository {
    @Override
    public Connection addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
       String[]sourceData=sourceCoordinate.replace("(","")
               .replace(")","").split(",");
       String[]destinationData=destinationCoordinate.replace("(","")
               .replace(")","").split(",");



        Point sourcePoint=new      Point(Integer.parseInt(sourceData[0]),Integer.parseInt(sourceData[1]));
        Point destinationPoint=new Point(Integer.parseInt(destinationData[0]),Integer.parseInt(destinationData[1]));






    Connection connection=new Connection(sourceSpaceId,sourcePoint,destinationSpaceId,destinationPoint);
        return connection;
    }
}
