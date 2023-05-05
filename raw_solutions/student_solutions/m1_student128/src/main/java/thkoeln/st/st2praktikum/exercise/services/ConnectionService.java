package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.Entity.Connection;
import thkoeln.st.st2praktikum.exercise.Entity.Point;
import thkoeln.st.st2praktikum.exercise.repos.ConnectionRepo;

import java.util.UUID;

public class ConnectionService implements ConnectionRepo {
    @Override
    public Connection addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        String[]sourceData=sourceCoordinate.replace("(","")
                .replace(")","").split(",");
        String[]destinationData=destinationCoordinate.replace("(","")
                .replace(")","").split(",");



        Point sourcePoint=new      Point(Integer.parseInt(sourceData[0]),Integer.parseInt(sourceData[1]));
        Point destinationPoint=new Point(Integer.parseInt(destinationData[0]),Integer.parseInt(destinationData[1]));







        Connection connection=new Connection(sourceFieldId,sourcePoint,destinationFieldId,destinationPoint);
        return connection;
    }
}
