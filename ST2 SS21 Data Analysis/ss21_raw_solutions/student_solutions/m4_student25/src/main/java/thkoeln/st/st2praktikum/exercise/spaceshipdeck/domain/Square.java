package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Square {
    @Id
    private UUID squareID = UUID.randomUUID();
    private Coordinate coordinates;
    @OneToOne(cascade = {CascadeType.ALL})
    private Connection connection;


    protected Square() {
    }

    public Square(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public Boolean hasConnection() {
        return connection != null;
    }
}
