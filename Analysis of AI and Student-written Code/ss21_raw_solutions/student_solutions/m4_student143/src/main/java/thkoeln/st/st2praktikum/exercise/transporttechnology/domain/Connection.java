package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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