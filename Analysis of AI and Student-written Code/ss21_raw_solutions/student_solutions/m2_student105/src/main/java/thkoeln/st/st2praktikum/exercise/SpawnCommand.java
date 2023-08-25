package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.entities.Deck;
import thkoeln.st.st2praktikum.exercise.interfaces.Controller;
import thkoeln.st.st2praktikum.exercise.interfaces.Spawnable;

@AllArgsConstructor
@Getter
public class SpawnCommand extends Command {
    public Deck deck;
    public Spawnable affected;

    @Override
    public boolean executeOn(Controller controller) {
        return controller.spawn(this);
    }
}