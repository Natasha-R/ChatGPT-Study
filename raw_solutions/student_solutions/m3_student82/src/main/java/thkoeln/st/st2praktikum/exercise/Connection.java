package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Connection extends AbstractEntity {

    @Id
    private UUID connectionId = UUID.randomUUID();

    @Setter
    private UUID sourceDeckId;

    @Setter
    private UUID destinationDeckId;

    @Setter
    private Coordinate sourceCoordinate;

    @Setter
    private Coordinate destinationCoordinate;

    private UUID transportCategoryId;


    public Connection(UUID transportCategoryId, UUID sourceDeckId, UUID destinationDeckId, Coordinate sourceCoordinate, Coordinate destinationCoordinate) {
        this.connectionId = getConnectionId();
        this.transportCategoryId = transportCategoryId;
        this.sourceDeckId = sourceDeckId;
        this.destinationDeckId = destinationDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }
}
