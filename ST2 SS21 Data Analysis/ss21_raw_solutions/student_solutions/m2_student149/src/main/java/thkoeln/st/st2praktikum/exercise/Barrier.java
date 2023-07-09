package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.environment.barrier.MyBarrier;
import thkoeln.st.st2praktikum.exercise.environment.position.Position;

/**
 * Nicht wundern: Ich nutze die neu vorgegebenen Klassen als Wrapper-Klassen, da meine Struktur bereits in M1 dem Design
 * entsprochen hat, allerdings teilweise mit erweiterter Funktion, daher ist ein einfaches Umschreiben nicht direkt
 * möglich...
 */
public class Barrier {

    private MyBarrier barrier;

    public Barrier(Vector2D pos1, Vector2D pos2) {
        this.barrier = MyBarrier.of(Position.of(pos1.getX(), pos1.getY()), Position.of(pos2.getX(), pos2.getY()));
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        this.barrier = MyBarrier.of(barrierString);
    }

    public Vector2D getStart() {
        return new Vector2D(barrier.getStart());
    }

    public Vector2D getEnd() {
        return new Vector2D(barrier.getEnd());
    }

    public MyBarrier getMyBarrier() {
        return barrier;
    }
}
