package thkoeln.st.st2praktikum.exercise.entities.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.entities.Deck;
import thkoeln.st.st2praktikum.exercise.entities.Spaceship;
import thkoeln.st.st2praktikum.exercise.interfaces.Spawnable;

@AllArgsConstructor
@Getter
public class SpawnCommand extends Command {
    public Deck deck;
    public Spawnable affected;

    @Override
    public boolean executeOn(Spaceship ship) {
        return ship.spawnDroid(this);
    }
}