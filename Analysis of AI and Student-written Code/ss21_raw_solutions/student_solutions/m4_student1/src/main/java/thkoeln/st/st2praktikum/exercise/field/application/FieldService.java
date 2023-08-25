package thkoeln.st.st2praktikum.exercise.field.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.field.repository.FieldRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class FieldService {

    private FieldRepository fieldRepository;

    @Autowired
    public void SpaceShipDeckRepository(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height, width);
        this.fieldRepository.save(field);

        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Optional<Field> optionalSpaceShipDeck = this.fieldRepository.findById(fieldId);
        if(optionalSpaceShipDeck.isEmpty()) {
            throw new RuntimeException();
        }

        Field spaceShipDeck = optionalSpaceShipDeck.get();
        spaceShipDeck.addBarrier(barrier);
    }

    public Field findById(UUID id) {
        return fieldRepository.findById(id).orElseThrow();
    }

    public Iterable<Field> findAll() {
        return fieldRepository.findAll();
    }
}
