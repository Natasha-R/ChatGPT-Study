package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.Entity;
@NoArgsConstructor
@Entity
public class TransportCategory extends AbstractEntity {

    private String categoryName;
    TransportCategory(String categoryName){
        this.categoryName=categoryName;
    }


}
