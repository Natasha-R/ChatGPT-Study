package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportCategory extends AbstractEntity {


    private UUID transportCategoryId;
    @Id
    public UUID getTransportCategoryId(){
        return transportCategoryId;
    }

    public void setId(UUID id) {
        this.transportCategoryId = id;
    }
    private String transportCategoryName;



    private List<Connection> listConnection = new ArrayList<>();

    @ElementCollection(targetClass =  thkoeln.st.st2praktikum.exercise.domainprimitives.Connection.class,fetch = FetchType.EAGER)
   public List<Connection> getListConnection(){
        return listConnection;
    }





    public TransportCategory( String transportCategoryName, UUID transportCategoryId) {
        this.transportCategoryName = transportCategoryName;
        this.transportCategoryId = transportCategoryId;
    }
    public String getTransportCategoryName(){
        return transportCategoryName;
    }



}
