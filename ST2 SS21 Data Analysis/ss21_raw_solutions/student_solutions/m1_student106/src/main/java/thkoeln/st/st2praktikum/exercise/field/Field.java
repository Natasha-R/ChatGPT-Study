package thkoeln.st.st2praktikum.exercise.field;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class Field {
    private UUID uuid = UUID.randomUUID();

    //boardes of the field
    private int width;
    private int height;

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
    }


}
