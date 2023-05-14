package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertThrows;



public class E2MyTests extends MovementTests {
    protected CleaningDeviceService service;

   //Placing a connection out of bounds must not be possible
   @BeforeEach
   public void setUPMyService(){
       service = new CleaningDeviceService();
       createWorld(service);
   }
   @Test
   public void placingAConnectionOutOfBounds(){

       assertThrows(RuntimeException.class, () ->service.addConnection(space1,new Coordinate("(0,7)"),space2,new Coordinate("6,0")));
       assertThrows(RuntimeException.class, () ->service.addConnection(space1,new Coordinate("(7,8)"),space3,new Coordinate("4,1")));
       assertThrows(RuntimeException.class, () ->service.addConnection(space2,new Coordinate("(9,1)"),space3,new Coordinate("4,7")));
       assertThrows(RuntimeException.class, () ->service.addConnection(space2,new Coordinate("(8,1)"),space1,new Coordinate("9,1")));

   }

//Placing a obstacle out of bounds must not be possible
   @Test
    public void PlacingAObstacleOutOfBounds (){

       assertThrows(RuntimeException.class,() -> service.addObstacle(space3,new Obstacle("(0,-1)-(2,1)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space3,new Obstacle("(0,0)-(2,-1)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space3,new Obstacle("(10,0)-(10,2)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space3,new Obstacle("(10,10)-(-12,10)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space3,new Obstacle("(0,0)-(0,10)")));

       assertThrows(RuntimeException.class,() -> service.addObstacle(space1,new Obstacle("(0,0)-(0,7)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space1,new Obstacle("(6,0)-(6,-1)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space1,new Obstacle("(10,7)-(10,8)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space1,new Obstacle("(10,10)-(12,10)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space1,new Obstacle("(0,0)-(0,10)")));

       assertThrows(RuntimeException.class,() -> service.addObstacle(space3,new Obstacle("(5,5)-(5,10)")));
       assertThrows(RuntimeException.class,() -> service.addObstacle(space1,new Obstacle("(0,0)-(0,10)")));
   }

   /*
   Hitting another cleaningDevice during a Move-Command has to interrupt the current movement
    Trying to move out of bounds must not be possible
    *Placing a connection out of bounds must not be possible
    Traversing a connection must not be possible in case the destination is occupied
    Using the TR-command must not be possible if there is no outgoing connection on the current position
    *Placing a obstacle out of bounds must not be possible
    */

    @Test
    public void  usingTheTrCommandMust(){

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space2 + "]"),
                new Order("[ea,4]"),
                new Order("[tr," + space3 + "]"),

        });

        assertPosition(service, cleaningDevice1, space2, 4, 0);

}




}
