package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import java.util.UUID;
@Getter
public class Connection{
    private UUID id =UUID.randomUUID();
    private UUID sourceFieldId;
    private Point sourceCoordinate;
    private UUID destinationFieldId;
    private Point destinationCoordinate;
    public Connection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate){
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = destinationCoordinate;
    }
    public Connection getConnection(){
        return this;
    }
}
