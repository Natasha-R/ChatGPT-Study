package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

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


    public Connection( UUID sourceDeckId, UUID destinationDeckId, Coordinate sourceCoordinate, Coordinate destinationCoordinate) {
        this.connectionId = getConnectionId();
        this.sourceDeckId = sourceDeckId;
        this.destinationDeckId = destinationDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }
}

