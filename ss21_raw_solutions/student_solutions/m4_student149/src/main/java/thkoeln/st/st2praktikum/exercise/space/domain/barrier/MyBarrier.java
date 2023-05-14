package thkoeln.st.st2praktikum.exercise.space.domain.barrier;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.BlockedTransition;
import thkoeln.st.st2praktikum.exercise.exception.InvalidBarrierException;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;
import thkoeln.st.st2praktikum.exercise.parser.BarrierParser;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class MyBarrier {

    private static final BarrierParser parser = new BarrierParser();

    @Embedded
    private Vector2D start;

    @Embedded
    private Vector2D end;

    private BarrierOrientation orientation;

    private List<BlockedTransition> blockedTransitions = new ArrayList<>();

    private MyBarrier(Vector2D barrierEdge1, Vector2D barrierEdge2) throws InvalidBarrierException {
        orderBarrier(barrierEdge1, barrierEdge2);
        initOrientation();
        initBlockedTransitions();
    }

    public static MyBarrier of(String string) throws ParseException {
        return parser.parse(string);
    }

    public static MyBarrier of(Vector2D start, Vector2D end) {
        return new MyBarrier(start, end);
    }

    protected void setBlockedTransitions(List<BlockedTransition> blockedTransitions) {
        this.blockedTransitions = blockedTransitions;
    }

    @OneToMany
    public List<BlockedTransition> getBlockedTransitions() {
        return blockedTransitions;
    }

    protected void setStart(Vector2D start) {
        this.start = start;
    }

    public Vector2D getStart() {
        return start;
    }

    protected void setEnd(Vector2D end) {
        this.end = end;
    }

    public Vector2D getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start + "-" + end;
    }

    private void orderBarrier(Vector2D barrierEdge1, Vector2D barrierEdge2) throws InvalidBarrierException {
        if (Objects.equals(barrierEdge1, barrierEdge2))
            throw new InvalidBarrierException("Start position should differ from end position", this);

        int deltaX = barrierEdge1.getX() - barrierEdge2.getX();
        int deltaY = barrierEdge1.getY() - barrierEdge2.getY();

        if (deltaX < 0 || deltaY < 0) {
            this.start = barrierEdge1;
            this.end = barrierEdge2;
        } else {
            this.start = barrierEdge2;
            this.end = barrierEdge1;
        }
    }

    private void initOrientation() throws InvalidBarrierException {
        int deltaX = end.getX() - start.getX();
        int deltaY = end.getY() - start.getY();

        if (deltaX != 0 && deltaY != 0) throw new InvalidBarrierException("Diagonal barrier is not allowed", this);

        if (deltaX != 0) this.orientation = BarrierOrientation.HORIZONTAL;
        else if (deltaY != 0) this.orientation = BarrierOrientation.VERTICAL;
    }

    private void initBlockedTransitions() {
        switch (orientation) {
            case VERTICAL:
                int x = start.getX();
                for (int i = start.getY(); i <= end.getY(); i++) {
                    blockedTransitions.add(new BlockedTransition(new Vector2D(x, i), new Vector2D(x - 1, i)));
                }
                break;
            case HORIZONTAL:
                int y = start.getY();
                for (int i = start.getX(); i < end.getX(); i++) {
                    blockedTransitions.add(new BlockedTransition(new Vector2D(i, y), new Vector2D(i, y - 1)));
                }
                break;
        }
    }
}
