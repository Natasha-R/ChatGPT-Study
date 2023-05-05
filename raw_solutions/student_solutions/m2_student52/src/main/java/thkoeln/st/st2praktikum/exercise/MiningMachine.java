package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
public class MiningMachine {
    @Getter private final String name;
    @Getter @Setter private Point point;
    @Getter @Setter private UUID fieldID;
}
