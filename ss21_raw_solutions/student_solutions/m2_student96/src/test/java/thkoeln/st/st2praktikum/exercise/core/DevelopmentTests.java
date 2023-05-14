package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.map.Grid;
import thkoeln.st.st2praktikum.exercise.Point;

public abstract class DevelopmentTests {

    protected Grid testGrid;

    protected Point testPoint;
    protected Point southernPoint;
    protected Point westernPoint;

    protected String testBarrierString1;
    protected String testBarrierString2;

    protected Barrier testBarrier1;
    protected Barrier testBarrier2;

    protected void createWorld() {

        testGrid = new Grid( 5, 5 );

        testPoint = testGrid.getPoints().get( "(1,1)" );
        southernPoint = testGrid.getPointFromGrid( "(1,0)" );
        westernPoint = testGrid.getPointFromGrid( "(0,1)" );

        testBarrierString1 = "(1,2)-(2,2)";
        testBarrierString2 = "(2,0)-(2,2)";

        testBarrier1 = new Barrier( testBarrierString1 );
        testBarrier2 = new Barrier( testBarrierString2 );

    }
}
