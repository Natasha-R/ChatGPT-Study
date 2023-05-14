package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class TransportCategory extends TidyUpRobotService  {
    @Id
    @Getter

    private UUID transportCategoryId = UUID.randomUUID() ;


    @Getter
    @Setter
    private String transportCategory;


    public TransportCategory(){}
    public TransportCategory(String transportCategory){
        this.transportCategory = transportCategory;
    }

}
