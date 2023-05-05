package thkoeln.st.st2praktikum.map;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thkoeln.st.st2praktikum.lib.AbstractEntity;

@RequiredArgsConstructor
@Getter
public class Connection extends AbstractEntity {
    private final MapPosition source;
    private final MapPosition target;
}
