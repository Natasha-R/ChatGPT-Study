package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.core.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.UUID;
@Entity
@Getter
@NoArgsConstructor
public class Connection extends AbstractEntity {

    @ManyToOne
    TransportTechnology transportTechnology;

    private UUID transportTechnologyid;
    private UUID entranceShipDeckID;
    @Transient
    private Coordinate entranceCoordinates;
    private UUID exitShipDeckID;
    @Transient
    private Coordinate exitCoordinates;

    public Connection(UUID transportTechnologyid,UUID entranceShipDeckID, Coordinate entranceCoordinates,UUID exitShipDeckID, Coordinate exitCoordinates){
        this.transportTechnologyid=transportTechnologyid;
        this.entranceShipDeckID=entranceShipDeckID;
        this.entranceCoordinates=entranceCoordinates;
        this.exitShipDeckID=exitShipDeckID;
        this.exitCoordinates=exitCoordinates;
    }

}
