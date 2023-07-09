package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class TransportCategory {

    @Id
    UUID id = UUID.randomUUID();

    String transportCategory;

    public TransportCategory(){}

    public TransportCategory(String category){
        this.transportCategory = category;
    }
}

