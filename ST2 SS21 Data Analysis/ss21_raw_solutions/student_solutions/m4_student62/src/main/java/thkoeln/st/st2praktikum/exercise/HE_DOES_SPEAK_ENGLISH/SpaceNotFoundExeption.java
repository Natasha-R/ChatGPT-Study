package thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH;

import java.util.UUID;

public class SpaceNotFoundExeption extends RuntimeException {
    public SpaceNotFoundExeption(UUID spaceid){
        super("Space: "+spaceid+" not Found!");
    }
    public SpaceNotFoundExeption(){
        super();
    }
}
