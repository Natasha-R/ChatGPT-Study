package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Fieldllist;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;
import thkoeln.st.st2praktikum.exercise.field.domain.WallRepository;


import java.util.UUID;


@Service
public class FieldService {

    Fieldllist fieldlList = new Fieldllist();

    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    WallRepository wallRepository;




    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID fielduuid = UUID.randomUUID();
        Field field = new Field(height-1, width-1, fielduuid);
        fieldRepository.save(field);
        fieldlList.add(field);


        Wall  fieldwallleftside =   Wall.fromString ("("+0+","+0+")-("+0+","+(height)+ ")");
        Wall  fieldwallbottom =     Wall.fromString("("+0+","+0+")-("+(width)+","+0 + ")");
        Wall  fieldwalltop =        Wall.fromString ("("+0+","+(height)+")-("+(height)+","+(width) + ")");
        Wall  fieldwallrightside=   Wall.fromString ("("+(width)+","+0+")-("+(width)+","+(height) + ")");
        addWall(fielduuid, fieldwallleftside);
        addWall(fielduuid, fieldwallbottom);
        addWall(fielduuid, fieldwalltop);
        addWall(fielduuid, fieldwallrightside);

        return fielduuid;
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {

        wallRepository.save(wall);
        for (Field field : fieldlList.getFieldList() ) {
            if(field.getFielduuid() == fieldId) {
                fieldRepository.deleteById(fieldId);
                field.getWalllist().add(wall);
                fieldRepository.save(field);
            }
        }
    }
}
