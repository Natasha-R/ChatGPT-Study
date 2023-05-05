package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;

import java.util.HashMap;
import java.util.UUID;


@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    private HashMap<UUID, Field> fields;
    
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        if (height < 0 || width < 0)
            throw new IllegalArgumentException("One or more parameters are negative!");

        if (fields == null)
            fields = new HashMap<>();

        UUID fieldID = UUID.randomUUID();
        Field field = new Field(height,width,fieldID);
        fields.put(fieldID, field);
        fieldRepository.save(field);
        return fieldID;
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        if (fieldId == null || fields.get(fieldId) == null || obstacle == null)
            throw new IllegalArgumentException();

        Field field = fields.get(fieldId);
        field.addObstacles(obstacle);
        fieldRepository.deleteById(fieldId);
        fieldRepository.save(field);
    }
}
