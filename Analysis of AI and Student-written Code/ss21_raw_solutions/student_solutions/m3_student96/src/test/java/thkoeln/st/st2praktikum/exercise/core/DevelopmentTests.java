package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.map.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.map.Locatable;

public abstract class DevelopmentTests {

    protected SpaceShipDeck testSpaceShipDeck;

    protected Locatable testPoint;
    protected Locatable southernPoint;
    protected Locatable westernPoint;

    protected String testBarrierString1;
    protected String testBarrierString2;

    protected Barrier testBarrier1;
    protected Barrier testBarrier2;

    protected void createWorld() {

        testSpaceShipDeck = new SpaceShipDeck( 5, 5 );

        testPoint = testSpaceShipDeck.getPoints().get( "(1,1)" );
        southernPoint = testSpaceShipDeck.getPointFromSpaceShipDeck( "(1,0)" );
        westernPoint = testSpaceShipDeck.getPointFromSpaceShipDeck( "(0,1)" );

        testBarrierString1 = "(1,2)-(2,2)";
        testBarrierString2 = "(2,0)-(2,2)";

        testBarrier1 = new Barrier( testBarrierString1 );
        testBarrier2 = new Barrier( testBarrierString2 );

    }
}
