package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@Service
public class FieldService
{
    private ArrayList<Field> fields;

    private FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository)
    {
        fields = new ArrayList<Field>();

        this.fieldRepository = fieldRepository;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width)
    {
        Field field = new Field(height, width);

        fields.add(field);
        updateDatabase();

        return field.getUuid();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier)
    {
        Optional<Field> field = fieldRepository.findById(fieldId);

        if(field.isPresent())
            field.get().getBarriers().add(barrier);
        else
            // Error

        updateDatabase();
    }

    public void updateDatabase()
    {
        // Clear repository
        fieldRepository.deleteAll();

        // Fill repository
        for(Field field : fields)
        {
            fieldRepository.save(field);
        }
    }
}
