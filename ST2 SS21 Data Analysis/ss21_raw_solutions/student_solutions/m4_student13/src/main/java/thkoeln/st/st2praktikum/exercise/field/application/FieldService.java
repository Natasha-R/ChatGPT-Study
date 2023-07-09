package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;



import java.util.UUID;


@Service
public class FieldService {

    private FieldRepository fieldRepository;

    @Autowired
    public FieldService (FieldRepository fieldRepository){
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {

        Field field = new Field(height,width);
        fieldRepository.save(field);
        field.determineNeighbor();
        fieldRepository.save(field);
        return field.getId();

        //throw new UnsupportedOperationException();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        fieldRepository.findById(fieldId).get().placeWall(wall);
    }
}
