package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.uuid.UUIDElement;

import java.util.UUID;
import java.util.function.Supplier;

public class UUIDElementNotFoundException extends IllegalArgumentException {

    public UUIDElementNotFoundException(Class<? extends UUIDElement> clazz, UUID uuid) {
        super("There is no " + clazz.getSimpleName() + " with id: " + uuid + " in this service");
    }

    public static Supplier<UUIDElementNotFoundException> getExceptionSupplier(Class<? extends UUIDElement> clazz, UUID uuid) {
        return () -> new UUIDElementNotFoundException(clazz, uuid);
    }
}
