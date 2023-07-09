package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.interfaces.Controller;
import thkoeln.st.st2praktikum.exercise.interfaces.Transportable;

@AllArgsConstructor
@Getter
public class TransportCommand extends Command {
    private final Deck destination;
    private final Transportable affected;

    @Override
    public boolean executeOn(Controller controller) {
        return controller.transport(this);
    }
}
