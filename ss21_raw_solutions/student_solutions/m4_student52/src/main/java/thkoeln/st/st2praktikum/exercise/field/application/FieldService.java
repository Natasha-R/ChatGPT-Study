package thkoeln.st.st2praktikum.exercise.field.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;


import java.util.UUID;


@Service
public class FieldService {
    @Getter private FieldRepo fieldRepo;

    @Autowired
    public  FieldService(FieldRepo fieldRepo){
        this.fieldRepo = fieldRepo;
    }
    
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        var f = new Field(height, width);
        fieldRepo.save(f);
        return f.getId();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        var field = fieldRepo.get(fieldId);
        field.getBarriers().add(barrier);
        fieldRepo.save(field);
    }
}
