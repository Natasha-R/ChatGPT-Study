package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class Connection {

 private UUID connectionID;

 private UUID sourceField;
 private UUID destinationField;

 private Point sourceCoordinates;
 private Point destinationCoordinates;

 public Connection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate) {
   this.connectionID = UUID.randomUUID();
   this.sourceField = sourceFieldId;
   this.destinationField = destinationFieldId;
   this.sourceCoordinates = sourceCoordinate;
   this.destinationCoordinates = destinationCoordinate;
 }
}
