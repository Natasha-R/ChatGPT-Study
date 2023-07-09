package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportCategory extends AbstractEntity {
    private UUID transportcategoryuuid;
    @Id

    public UUID getId() {
        return transportcategoryuuid;
    }

    public void setId(UUID id) {
        this.transportcategoryuuid = id;
    }




    private String categoryname;

    private List<Connection> listConnection = new ArrayList<>();


    @ElementCollection(targetClass = Connection.class , fetch = FetchType.EAGER)
    public List<Connection> getListConnection() {
        return listConnection;
    }




    public TransportCategory(UUID transportcategoryuuid, String category) {
        this.transportcategoryuuid = transportcategoryuuid;
        this.categoryname = category;
    }






}
