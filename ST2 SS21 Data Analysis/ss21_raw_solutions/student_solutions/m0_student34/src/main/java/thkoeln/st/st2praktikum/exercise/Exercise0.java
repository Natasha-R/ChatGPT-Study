package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.droid.MaintenanceDroid;
import thkoeln.st.st2praktikum.map.LinearSystem;
import thkoeln.st.st2praktikum.map.Map;
import thkoeln.st.st2praktikum.parser.MovementSerializer;
import thkoeln.st.st2praktikum.parser.Parser;

@Component
@AllArgsConstructor
public class Exercise0 implements Moveable {

    private final Map map;
    private final MaintenanceDroid droid;
    private final MovementSerializer serializer;

    public Exercise0() {
        this.map = Map.defaultMap();
        this.droid = new MaintenanceDroid(map, MaintenanceDroid.defaultPosition(), new LinearSystem());
        serializer = new MovementSerializer();
    }

    @Override
    public String move(String moveCommandString) {
        var movement = Parser.getInstance().parse(moveCommandString);
        this.droid.move(movement);
        return serializer.serialize(this.droid.getPosition());
    }
}
