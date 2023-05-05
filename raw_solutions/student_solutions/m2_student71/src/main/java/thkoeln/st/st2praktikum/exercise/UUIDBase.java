package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import java.util.UUID;

public abstract class UUIDBase {

    @Getter
    private UUID uuid;

    public UUIDBase(){
        uuid = UUID.randomUUID();
    }

}
