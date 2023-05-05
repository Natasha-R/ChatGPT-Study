package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Connection {
    @Getter private final Coords src;
    @Getter private final Coords dest;
}
