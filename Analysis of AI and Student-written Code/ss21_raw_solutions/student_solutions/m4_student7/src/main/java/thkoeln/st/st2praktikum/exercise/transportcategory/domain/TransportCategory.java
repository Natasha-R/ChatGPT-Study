package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportCategory {
    @Id
    private UUID id = UUID.randomUUID();

    private String categoryName;

    public TransportCategory(String name){
        this.categoryName = name;
    }

}
