package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;
import java.util.Optional;


@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RectangleObstacle extends Rectangle implements IObstacle {

    @Embedded
    private Vector upperLeftCorner;
    @Embedded
    private Vector lowerLeftCorner;
    @Embedded
    private Vector upperRightCorner;
    @Embedded
    private Vector lowerRightCorner;

    @Override
    @Transient
    public BoundedObstacle[] getSites() {
        return new BoundedObstacle[]{
                new BoundedObstacle(this.getUpperLeftCorner(), this
                        .getUpperRightCorner()),
                new BoundedObstacle(this.getLowerLeftCorner(), this
                        .getLowerRightCorner()),
                new BoundedObstacle(this.getLowerLeftCorner(), this
                        .getUpperLeftCorner()),
                new BoundedObstacle(this.getLowerRightCorner(), this
                        .getUpperRightCorner())
        };
    }

    @Override
    public Optional<Vector> cut(Cuttable cuttable) {
        if (cuttable instanceof Movement) {
            return new RectangleMovementCutting(this, (Movement) cuttable)
                    .calculateCut();
        }
        return super.cut(cuttable);
    }
}
