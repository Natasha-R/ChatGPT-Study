package thkoeln.st.st2praktikum.exercise.field.application;

import thkoeln.st.st2praktikum.exercise.field.domain.*;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Service
public class FieldService {
    private final HashMap<UUID, Field> fields = new HashMap<>();

    private final FieldRepository fieldRepository;

    @Autowired
    private FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height, width);
        fields.put(field.getId(), field);
        fieldRepository.save(field);
        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = fields.get(fieldId);
        if (barrier.getEnd().getX() <= field.getWidth() && barrier.getEnd().getY() <= field.getHeight()) {
            field.getBarriers().add(barrier);
            fields.put(field.getId(), field);
            fieldRepository.save(field);
        } else throw new IllegalArgumentException("A barrier can't extend beyond the field it's being placed on!");
    }
}
