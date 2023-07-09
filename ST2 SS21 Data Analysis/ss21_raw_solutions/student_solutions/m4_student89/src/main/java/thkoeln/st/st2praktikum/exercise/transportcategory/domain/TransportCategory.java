package thkoeln.st.st2praktikum.exercise.transportcategory.domain;


import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.core.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class TransportCategory extends AbstractEntity {

    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Connection> connectionList  =new ArrayList<Connection>();

   public TransportCategory(String categoryName){
        this.categoryName=categoryName;
    }


}