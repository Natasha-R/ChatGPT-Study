package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.ObjectHolder;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineCommands;


import java.util.UUID;


@Service
public class FieldService {

    private MiningMachineCommands commands = ObjectHolder.getMiningMachineCommands();
    @Autowired
    private FieldRepository fieldRepository;

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field temp = new Field();
        temp.setHeight(height);
        temp.setWidth(width);
        commands.getFields().add(temp);
        fieldRepository.save(temp);
        return temp.getId();

    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field temp = commands.findField(fieldId);
        if(wall.getStart().getX() >= 0 && wall.getStart().getY() >= 0 && wall.getEnd().getX() <= temp.getWidth() && wall.getEnd().getY() <= temp.getHeight()){
            temp.addWall(wall);
            fieldRepository.save(temp);
        }else{
            throw new RuntimeException("wall out of bounds");
        }

    }
}
