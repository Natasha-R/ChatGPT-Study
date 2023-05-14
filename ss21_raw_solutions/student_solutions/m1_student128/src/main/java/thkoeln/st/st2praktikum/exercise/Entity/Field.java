package thkoeln.st.st2praktikum.exercise.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Setter
@Getter
public class Field {
    protected UUID fieldId;
    protected int height,width;
    protected List<Wall> wallList=new ArrayList<>();
    protected List<Connection> connectionList=new ArrayList<>();
    protected List<MiningMachine> MiningMachineList=new ArrayList<>();

    public Field() {

    }

    public Field(int height, int width) {
        this.fieldId= UUID.randomUUID();
        this.height = height;
        this.width = width;

    }
}
