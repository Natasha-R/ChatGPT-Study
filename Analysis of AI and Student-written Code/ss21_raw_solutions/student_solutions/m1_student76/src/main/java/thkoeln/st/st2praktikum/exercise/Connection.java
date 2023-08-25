package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;


@Getter
@Setter
public class Connection extends AbstractEntity {

    private UUID sourceSpaceShipDeckId;
    private UUID destinationSpaceShipDeckId;

    private Point sourceCoordinate;
    private Point destinationCoordinate;

}
