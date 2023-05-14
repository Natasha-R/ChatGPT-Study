package thkoeln.st.st2praktikum.exercise;



import lombok.Getter;
import lombok.Setter;


import java.util.UUID;


@Getter
@Setter
public class MiningMachine {


    UUID MachineID;

    String name;
    int coordinateX;
    int coordinateY;



    @Override
    public String toString() {
        return "MiningMachine{" +
                "MachineID=" + MachineID +
                ", name='" + name + '\'' +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", fieldID=" + fieldID +
                '}';
    }


    UUID fieldID ;



}
