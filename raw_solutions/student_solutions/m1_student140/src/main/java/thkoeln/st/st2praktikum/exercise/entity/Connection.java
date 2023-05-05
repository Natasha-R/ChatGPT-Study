package thkoeln.st.st2praktikum.exercise.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection {
    @Id
    private UUID connectionId;
    @ManyToOne
    @JoinColumn(name = "SourceSpace_ID")
    private Space sourceSpace;
    @ManyToOne
    @JoinColumn(name = "DestinationSpace_ID")
    private Space destinationSpace;

    private String sourceCoordinates;
    private String destinationCoordinates;


    public Connection(UUID connectionId, Space sourceSpace, Space destinationSpace, String sourceCoordinates, String destinationCoordinates) {
        this.connectionId = connectionId;
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.sourceCoordinates = sourceCoordinates;
        this.destinationCoordinates = destinationCoordinates;
    }

    public String getSourceCoordinates() {
        return this.sourceCoordinates;
    }

    public String getDestinationCoordinates() {
        return this.destinationCoordinates;
    }

    public Space getSourceSpace() {
        return this.sourceSpace;
    }

    public Space getDestinationSpace() {
        return this.destinationSpace;
    }

}
