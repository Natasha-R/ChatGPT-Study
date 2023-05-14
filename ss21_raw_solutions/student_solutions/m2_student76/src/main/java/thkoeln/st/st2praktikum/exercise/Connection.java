package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import java.util.UUID;
@Getter
@Setter
public class Connection extends AbstractEntity {

    private UUID sourceSpaceDeck;
    private UUID destinationSpaceDeck;
    private Point sourcePoint;
    private Point destinationPoint;

    public Connection(UUID sourceSpaceDeck,UUID destinationSpaceDeck, Point sourcePoint, Point destinationPoint){
        this.sourceSpaceDeck = sourceSpaceDeck;
        this.destinationSpaceDeck = destinationSpaceDeck;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;

    }

}
