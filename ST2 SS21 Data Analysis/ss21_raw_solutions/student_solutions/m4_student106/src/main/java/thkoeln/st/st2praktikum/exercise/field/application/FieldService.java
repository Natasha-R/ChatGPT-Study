package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;



import java.util.UUID;


@Service
public class FieldService {
    @Autowired
    FieldRepository fieldRepository;

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field newField = new Field(height, width);
        fieldRepository.save(newField);
        return newField.getUuid();
    }

    /**
     * This method adds a obstacle to a given field.
     *
     * @param fieldId  the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        fieldRepository.findById(fieldId).get().ceckPointToBeWithinBoarders(obstacle.getStart());
        fieldRepository.findById(fieldId).get().ceckPointToBeWithinBoarders(obstacle.getEnd());
        fieldRepository.findById(fieldId).get().addObstacle(obstacle);
    }
}
