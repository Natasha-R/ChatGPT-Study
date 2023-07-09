package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Repositories.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;


import java.util.UUID;


@Service
public class FieldService {
    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width,height);
        fieldRepository.save(field);
        return field.getFieldID();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        for (Field value : fieldRepository.findAll()) {
            if (value.getFieldID() == fieldId) {
              /*  if(barrierString.getStart().getX() > value.getWidth()
                        || barrierString.getEnd().getX() > value.getWidth()
                        || barrierString.getStart().getY() > value.getHeight()
                        || barrierString.getEnd().getY() > value.getHeight())throw new RuntimeException("barrier out of bounds");

               */
                value.getBarriers().add(barrier);
            }
        }
    }
}
