package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Getter
@Setter
public class Connection {

    UUID connectionID;

    UUID sourceField;
    UUID destinationField;
    String sourceCoordinates;
    String destinationCoordinates;

    int destinationCoordinateX;
    int destinationCoordinateY;




}
