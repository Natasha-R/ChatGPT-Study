package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class SpaceNotFoundExeption extends Exception {
    public SpaceNotFoundExeption(UUID spaceid){
        super("Space: "+spaceid+" not Found!");
    }
}
