package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.field.domain.TileRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;


import javax.management.InvalidAttributeValueException;
import java.util.UUID;


@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final TileRepository tileRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository, TileRepository tileRepository) {
        this.fieldRepository = fieldRepository;
        this.tileRepository = tileRepository;
    }



    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        var field = new Field(height,width);
        fieldRepository.save(field);

        var Tiles = field.getBoard();

        for (int i = 0; i < height*width; i++) {
            tileRepository.save(Tiles.get(i));
        }

        fieldRepository.save(field);

        return field.getUuid();
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        try {
            fieldRepository.findById(fieldId).get().addObstacle(obstacle);

        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }
}
