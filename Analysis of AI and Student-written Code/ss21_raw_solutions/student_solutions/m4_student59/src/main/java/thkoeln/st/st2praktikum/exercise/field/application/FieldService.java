package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;


import java.util.UUID;


@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository){
        this.fieldRepository = fieldRepository;
    }


    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        final Field field = new Field(height, width);
        this.fieldRepository.save(field);
        return field.getFieldId();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        this.fieldRepository.findById(fieldId).get().addBarrier(barrier);
    }
}
