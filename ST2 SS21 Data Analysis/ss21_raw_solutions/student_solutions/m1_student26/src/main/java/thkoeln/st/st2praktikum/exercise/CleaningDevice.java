package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class CleaningDevice extends AbstractEntity {

    @Getter
    private String name;
    @Getter @Setter
    private UUID currentSpace;
    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;

    public CleaningDevice(String name){
        this.name = name;
    }


}
