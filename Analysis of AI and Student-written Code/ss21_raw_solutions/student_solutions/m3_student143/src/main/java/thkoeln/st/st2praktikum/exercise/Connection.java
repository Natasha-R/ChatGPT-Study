package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @ManyToOne
    @Getter
    @Setter
    private Field sourceField;

    @ManyToOne
    @Getter
    @Setter
    private Field destinationField;

    @Embedded
    @Getter
    @Setter
    private Coordinate sourceCoordinate;

    @Embedded
    @Getter
    @Setter
    private Coordinate destinationCoordinate;


}
