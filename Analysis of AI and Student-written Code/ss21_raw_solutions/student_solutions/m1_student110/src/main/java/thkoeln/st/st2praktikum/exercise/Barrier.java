package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;


 class Barrier implements IdReturnable_Interface{


    private UUID uuidBarrier = UUID.randomUUID();

    UUID roomUUID;

    String barrierString;

    public Barrier(UUID roomUUID,String barrierString) {
        this.roomUUID=roomUUID;
        this.barrierString=barrierString;

    }


    @Override
    public UUID returnUUID() {
        return roomUUID;
    }
}


