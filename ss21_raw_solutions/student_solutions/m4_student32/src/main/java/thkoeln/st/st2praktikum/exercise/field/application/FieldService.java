package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;


import java.util.UUID;


@Service
public class FieldService {
    private FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public Field findFieldById(UUID id) {
        return fieldRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No dungeon with ID " + id + " can be found."));
    }

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID fieldID = UUID.randomUUID();
        fieldRepository.save(new Field(fieldID, height, width));
        return fieldID;
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = findFieldById(fieldId);
        if (barrier.getStart().getX() > field.getWidth() || barrier.getEnd().getX() > field.getWidth() ||
                barrier.getStart().getY() > field.getHeight() || barrier.getEnd().getY() > field.getHeight()) {
            throw new RuntimeException("Barrier out of Bounds");
        }
        field.addBarrier(barrier);
    }
}
