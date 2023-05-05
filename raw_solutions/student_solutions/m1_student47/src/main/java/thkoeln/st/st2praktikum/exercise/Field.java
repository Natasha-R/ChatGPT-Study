package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
public class Field {

    UUID FieldID;

    int width;
    int height;

    ArrayList<Barrier> barrierList = new ArrayList<>();
    ArrayList<Connection> connectionList = new ArrayList<>();

    @Override
    public String toString() {
        return "Field{" +
                "FieldID=" + FieldID +
                ", width=" + width +
                ", height=" + height +
                ", governsBarriers=" + barrierList +
                '}';
    }
}
