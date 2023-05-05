package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.Core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection extends AbstractEntity {
    @ManyToOne
    private TransportCategory transportCategory;

    private UUID startSpaceId;
    private Point startPoint;

    private UUID endSpaceId;
    private Point endPoint;

    public Connection(Space startSpace, Point startPoint, Space endSpace, Point endPoint) {
        this.startSpaceId = startSpace.getId();
        this.startPoint = startPoint;
        this.endSpaceId = endSpace.getId();
        this.endPoint = endPoint;
    }
}
