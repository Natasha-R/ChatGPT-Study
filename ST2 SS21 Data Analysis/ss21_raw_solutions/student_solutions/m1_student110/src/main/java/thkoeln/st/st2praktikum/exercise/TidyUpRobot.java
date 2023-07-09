package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;


class TidyUpRobot implements IdReturnable_Interface {

    public UUID uuidRobot = UUID.randomUUID();
    String robotName;
    UUID roomUUID;

    public TidyUpRobot(String robotName){
        this.robotName = robotName;
    }

    @Override
    public UUID returnUUID() {
        return uuidRobot;
    }



}

