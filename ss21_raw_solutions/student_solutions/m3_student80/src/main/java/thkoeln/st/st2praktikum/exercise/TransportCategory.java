package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;

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



    @ElementCollection(fetch = FetchType.EAGER)
    public List<Connection> getListConnection() {
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
