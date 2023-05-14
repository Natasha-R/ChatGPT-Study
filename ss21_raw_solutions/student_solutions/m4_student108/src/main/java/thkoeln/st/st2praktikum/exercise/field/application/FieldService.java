package thkoeln.st.st2praktikum.exercise.field.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;


import java.util.Optional;
import java.util.UUID;


@Service
public class FieldService {

    @Getter
    @Autowired
    private FieldRepository fieldRepository;

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width, height);
        fieldRepository.save(field);
        return field.getUUID();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Optional<Field> fieldOptional = fieldRepository.findById(fieldId);
        fieldOptional.get().addWall(wall);
        fieldRepository.save(fieldOptional.get());
    }
}
