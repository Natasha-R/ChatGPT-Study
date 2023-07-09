package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class SpaceFactory {

    public static HashMap<UUID, SpaceInterface> spaces = new HashMap<>();
    public static SpaceInterface CreateSpace(Integer height, Integer width){
        Space space = new Space(height, width);
        spaces.put(space.getId(), space);
        return space;
    }

    public static HashMap<UUID, SpaceInterface> getSpaces() {
        return spaces;
    }
}
