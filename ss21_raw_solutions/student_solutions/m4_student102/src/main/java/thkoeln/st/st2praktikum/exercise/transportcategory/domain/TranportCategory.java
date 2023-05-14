package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
public class TranportCategory {
    @Id
    private final UUID id=UUID.randomUUID();
    private String category;
    public TranportCategory(){}
    public TranportCategory( String category){
        this.category = category ;
    }

}
