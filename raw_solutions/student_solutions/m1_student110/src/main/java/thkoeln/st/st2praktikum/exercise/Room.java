package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


public class Room implements IdReturnable_Interface {


    private UUID uuidRoom = UUID.randomUUID();

    Integer height;
    Integer width;

    public Room(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public UUID returnUUID() {
        return uuidRoom;
    }


}
