package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Spaceship;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceshipRepository;


import java.util.ArrayList;
import java.util.UUID;


@Service
public class SpaceShipDeckService {
    @Autowired
    private SpaceshipRepository spaceshipRepository;

    private ArrayList<Barrier> allBarrier = new ArrayList<>();

    public UUID addSpaceShipDeck(Integer height, Integer width) {
        Spaceship spaceShip=new Spaceship();
        spaceShip.setHeight(height);
        spaceShip.setWidth(width);
        //make consistent
        spaceshipRepository.save(spaceShip);
        return spaceShip.getSpaceChipId();

    }


    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        char alignmentType = ' ';
        barrier.setIdSpaceShip(spaceShipDeckId);

        if (barrier.getStart().getX().equals(barrier.getEnd().getX()))
            alignmentType = 'V';
        if (barrier.getStart().getY().equals(barrier.getEnd().getY()))
            alignmentType = 'H';
        barrier.setBarrierType(alignmentType);
        if (getSpaceShip(spaceShipDeckId).getHeight() <= barrier.getStart().getX() || getSpaceShip(spaceShipDeckId).getWidth() <= barrier.getStart().getY())
            throw new RuntimeException("choose a valid number");
        else if (getSpaceShip(spaceShipDeckId).getHeight() <= barrier.getEnd().getX() || getSpaceShip(spaceShipDeckId).getWidth() <= barrier.getEnd().getY())
            throw new RuntimeException("choose a valid number");
        else
            allBarrier.add(barrier);

    }
    public Spaceship getSpaceShip(UUID idSpaceShip){
        return spaceshipRepository.findById(idSpaceShip).get();
    }
    public ArrayList<Barrier> getAllBarrier(){
        return this.allBarrier;
    }

}
