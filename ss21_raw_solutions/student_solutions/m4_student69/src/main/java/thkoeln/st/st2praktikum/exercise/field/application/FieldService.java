package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;


import java.util.UUID;


@Service
public class FieldService {
    public FieldRepo fieldRepo;

    @Autowired
    public FieldService(FieldRepo fieldRepo) {
        this.fieldRepo = fieldRepo;
    }
    
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(UUID.randomUUID(), height, width);
        fieldRepo.save(field);
        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = fieldRepo.findById(fieldId).get();
        if (barrier.getStart().getX().equals(barrier.getEnd().getX())) {
            field.getHORIZONTAL().add(barrier);
        } else if (barrier.getStart().getY().equals(barrier.getEnd().getY())) {
            field.getVERTICAL().add(barrier);
        }
        fieldRepo.save(field);
    }
}
