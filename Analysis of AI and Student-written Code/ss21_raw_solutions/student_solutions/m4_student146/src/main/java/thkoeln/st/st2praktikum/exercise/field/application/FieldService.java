package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.SingleWall;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;


import java.util.UUID;


@Service
public class FieldService {
    public final FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(UUID.randomUUID(), height, width);
        fieldRepository.save(field);
        return field.getId();
    }

    /**
     * This method adds a wall to a given field.
     *
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall    the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field field = fieldRepository.findById(fieldId).orElse(null);
        if (field == null)
            return;
        int fromX = wall.getStart().getX();
        int fromY = wall.getStart().getY();
        int toX = wall.getEnd().getX();
        int toY = wall.getEnd().getY();

        if (fromX == toX) {
            for (int i = fromY; i < toY; i++) {
                SingleWall singleWall = new SingleWall(fromX, i);
                field.getVerticalWalls().add(singleWall);
            }
        } else {
            for (int i = fromX; i < toX; i++) {
                SingleWall singleWall = new SingleWall(i, fromY);
                field.getHorizontalWalls().add(singleWall);
            }
        }
        fieldRepository.save(field);
    }
}
