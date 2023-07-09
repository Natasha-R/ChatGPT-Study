package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
