package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;


 class CurrentCoordinate implements IdReturnable_Interface {


   private UUID uuidRobot;


    Integer x;


    Integer y;

    public CurrentCoordinate(Integer x, Integer y, UUID uuidRobot){
        this.x = x;
        this.y = y;
        this.uuidRobot = uuidRobot;
    }


    @Override
    public UUID returnUUID() {
        return uuidRobot;
    }
}
