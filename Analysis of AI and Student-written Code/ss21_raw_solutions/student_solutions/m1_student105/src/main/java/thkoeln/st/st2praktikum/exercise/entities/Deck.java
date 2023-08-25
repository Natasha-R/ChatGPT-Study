package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.entities.commands.MoveCommand;
import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;
import thkoeln.st.st2praktikum.exercise.interfaces.Blocker;
import thkoeln.st.st2praktikum.exercise.interfaces.Grid;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void addBarrier(String barrierString) {
        try {
            Coordinate p1 = Coordinate.fromString(barrierString.split("-")[0]);
            Coordinate p2 = Coordinate.fromString(barrierString.split("-")[1]);
            this.blockers.add(new Barrier(p1, p2));
        } catch (ParseException pe) {
            System.out.println("Could not parse barrier");
        }

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
            if (b.blocks(mc)) {
                return true;
            }
        } return false;
    }
}
