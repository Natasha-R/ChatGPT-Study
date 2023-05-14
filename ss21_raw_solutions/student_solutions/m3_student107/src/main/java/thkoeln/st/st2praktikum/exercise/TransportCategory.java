package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportCategory implements Unique {
    @Id
    private UUID id;
    private String category;
    @OneToMany
    private Map<UUID,Connection> Connectionmap = new HashMap<UUID,Connection>();



    public TransportCategory(String category){
        this.category = category;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return this.id;
    }

    public String getCategory() {
        return this.category;
    }

    public Map<UUID, Connection> getConnectionHashmap() {
        return this.Connectionmap;
    }

    public Connection getConnectionById(UUID id){
        return this.Connectionmap.get(id);
    }

    public void addConnection(Connection connection){
        this.getConnectionmap().put(connection.getId(),connection);
    }

}
