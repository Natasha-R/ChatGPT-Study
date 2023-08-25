package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;


 class Connection implements IdReturnable_Interface{


    private UUID uuidConnection = UUID.randomUUID();


    String sourceCoordinate;


     UUID sourceRoomId;

     UUID destinationRoomId;


    String destinationCoordinate;

    public Connection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate){
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }


    @Override
    public UUID returnUUID() {
        return this.uuidConnection;
    }
}
