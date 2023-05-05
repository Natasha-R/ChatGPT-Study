package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.environment.barrier.MyBarrier;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Nicht wundern: Ich nutze die neu vorgegebenen Klassen als Wrapper-Klassen, da meine Struktur bereits in M1 dem Design
 * entsprochen hat, allerdings teilweise mit erweiterter Funktion, daher ist ein einfaches Umschreiben nicht direkt
 * möglich...
 */
@Embeddable
@NoArgsConstructor
public class Barrier {

    @Embedded
    private Vector2D vector2DStart;

    @Embedded
    private Vector2D vector2DEnd;

    @Embedded
    private MyBarrier barrier;

    public Barrier(Vector2D pos1, Vector2D pos2) {
        setMyBarrier(pos1, pos2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        this.barrier = MyBarrier.of(barrierString);
    }

    public void setStart(Vector2D start) {
        this.vector2DStart = start;
        refreshMyBarrier();
    }

    public Vector2D getStart() {
        return barrier.getStart();
    }

    public void setEnd(Vector2D end) {
        this.vector2DEnd = end;
        refreshMyBarrier();
    }

    public Vector2D getEnd() {
        return barrier.getEnd();
    }

    protected void setMyBarrier(MyBarrier barrier) {
        this.barrier = barrier;
    }

    public MyBarrier getMyBarrier() {
        return barrier;
    }

    private void setMyBarrier(Vector2D vector2DStart, Vector2D vector2DEnd) {
        barrier = MyBarrier.of(vector2DStart, vector2DEnd);
    }

    private void refreshMyBarrier() {
        if (vector2DStart != null && vector2DEnd != null) {
            setMyBarrier(vector2DStart, vector2DEnd);
        }
    }
}
