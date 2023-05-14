package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.FieldComponent.IComponent;

import java.util.UUID;

public interface IUUIDManager {
    void rememberObject(IComponent gameComponent);
    IComponent getObjectFromUUID(UUID id);
    IComponent getObjectFromUUIDAsString(String id);
}
