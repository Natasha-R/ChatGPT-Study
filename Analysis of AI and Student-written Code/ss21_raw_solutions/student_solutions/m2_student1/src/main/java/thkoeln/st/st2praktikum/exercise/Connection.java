package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private final UUID connectionID;
    private final UUID sourceField;
    private final UUID destinationField;
    private final Point sourcePoint;
    private final Point destinationPoint;
    MiningMachineService mms = new MiningMachineService();

    public Connection(UUID sourceField, UUID destinationField, Point sourcePoint, Point destinationPoint) {
        this.connectionID = UUID.randomUUID();
        this.sourceField = sourceField;
        this.destinationField = destinationField;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
    }


    public UUID getConnectionID(){return connectionID;}

    public UUID getStartID(){return sourceField;}

    public UUID getEndID(){return destinationField;}

    public Point getStartLocation(){return sourcePoint;}

    public Point getEndLocation(){return destinationPoint;}


}
