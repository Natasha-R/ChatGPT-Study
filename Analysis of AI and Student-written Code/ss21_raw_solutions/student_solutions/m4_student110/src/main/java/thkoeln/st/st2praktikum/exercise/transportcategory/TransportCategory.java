package thkoeln.st.st2praktikum.exercise.transportcategory;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class TransportCategory  {
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
