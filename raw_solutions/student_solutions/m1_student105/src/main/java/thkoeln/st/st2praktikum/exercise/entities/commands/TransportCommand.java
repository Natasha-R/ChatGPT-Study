package thkoeln.st.st2praktikum.exercise.entities.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.entities.Deck;
import thkoeln.st.st2praktikum.exercise.entities.Spaceship;
import thkoeln.st.st2praktikum.exercise.interfaces.Transportable;

@AllArgsConstructor
@Getter
public class TransportCommand extends Command {
    private final Deck destination;
    private final Transportable affected;

    @Override
    public boolean executeOn(Spaceship ship) {
        return ship.transport(this);
    }
}
