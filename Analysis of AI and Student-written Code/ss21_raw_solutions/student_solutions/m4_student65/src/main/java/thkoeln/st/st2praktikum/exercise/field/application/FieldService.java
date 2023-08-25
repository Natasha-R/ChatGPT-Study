package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Field;
import thkoeln.st.st2praktikum.exercise.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class FieldService {

    private HashMap<UUID, Field> fields = new HashMap<>();

    @Autowired
    private FieldRepository fieldRepository;
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID uuid = UUID.randomUUID();
        fields.put(uuid,new Field(height, width,uuid));
        fieldRepository.save(fields.get(uuid));
        return uuid;
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        fields.get(fieldId).addObstacle(obstacle);
        fieldRepository.save(fields.get(fieldId));
        List<Field> fields = Streamable.of(fieldRepository.findAll()).toList();
        int k=23;
    }
}
