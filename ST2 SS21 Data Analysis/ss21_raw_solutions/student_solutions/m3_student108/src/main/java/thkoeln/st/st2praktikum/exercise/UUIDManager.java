package thkoeln.st.st2praktikum.exercise;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class UUIDManager {
    @Id
    private UUID id = UUID.randomUUID();

    @Transient
    List<IComponent> UUIDToObject = new ArrayList();

    public void addGameComponent(IComponent gameComponent) {
        UUIDToObject.add(gameComponent);
    }

    public IComponent getObjectFromUUID(UUID id) {
        for (int index=0; index<UUIDToObject.size(); index++) {
            if (UUIDToObject.get(index).getUUID() == id || UUIDToObject.get(index).getUUID().toString().equals(id.toString())) {
                return UUIDToObject.get(index);
            }
        }
        return null;
    }

    public UUID getUUIDFromObject(IComponent gameComponent) {
        for (int index=0; index<UUIDToObject.size(); index++) {
            if (UUIDToObject.get(index) == gameComponent) {
                return UUIDToObject.get(index).getUUID();
            }
        }
        return null;
    }
}
