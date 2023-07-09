package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.MoveCommand;
import thkoeln.st.st2praktikum.exercise.interfaces.Blocker;
import thkoeln.st.st2praktikum.exercise.interfaces.Grid;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class Deck extends AbstractEntity implements Grid {

    public int WIDTH;
    public int HEIGHT;

    Set<Blocker> blockers = new HashSet<>();

    public Deck(int h, int w) {
        this.HEIGHT = h;
        this.WIDTH = w;
    }

    public void addBarrier(Barrier barrier) {
        this.blockers.add(barrier);
    }

    @Override
    public void addBlocker(Blocker blocker) {
        this.blockers.add(blocker);
    }

    @Override
    public void removeBlocker(Blocker blocker) {
        this.blockers.remove(blocker);
    }

    @Override
    public int getWidth() {
        return this.WIDTH;
    }

    @Override
    public int getHeight() {
        return this.HEIGHT;
    }

    public boolean isBlocked(MoveCommand mc) {
        for (Blocker b : this.blockers) {
            if (b.blocks(mc) || isOutOfBounds(mc)) {
                return true;
            }
        } return false;
    }

    private boolean isOutOfBounds(MoveCommand mc) {
        return mc.endPosition.getX() < 0 ||mc.endPosition.getX() > this.WIDTH ||mc.endPosition.getY() < 0 ||mc.endPosition.getY() > this.HEIGHT;
    }
}
