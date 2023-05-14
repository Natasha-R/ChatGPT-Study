package thkoeln.st.st2praktikum.exercise;

import java.util.*;


public class MiningMachineService {
    public Field field;
    public MiningMachine machine;
    public Barrier barrier;
    public Connection connection;

    ArrayList<Field> fieldList = new ArrayList<>();
    ArrayList<MiningMachine> machineList = new ArrayList<>();

    public static String
    removeFirstandLast(String str)
    {
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(str.length() - 1);
        sb.deleteCharAt(0);
        return sb.toString();
    }

    public void barrierParser(String barrierstring){
        String[] inputs = barrierstring.split("-");

        String start = removeFirstandLast(inputs[0]);
        String[] coordinatesStart = start.split(",");

        String end = removeFirstandLast(inputs[1]);
        String[] coordinatesEnd = end.split(",");


        if(coordinatesStart[0].equals(coordinatesEnd[0])){

            barrier.setLeveX(Integer.parseInt(coordinatesEnd[0]));
            barrier.setBarrierstart(Integer.parseInt(coordinatesStart[1]));
            barrier.setBarrierend(Integer.parseInt(coordinatesEnd[1]));

            if(barrier.getBarrierend() < barrier.barrierend){
                int temp = barrier.getBarrierend();
                barrier.setBarrierend(barrier.barrierstart);
                barrier.setBarrierend(temp);
            }

        }

        if(coordinatesStart[1].equals(coordinatesEnd[1])){
            barrier.setLeveY(Integer.parseInt(coordinatesEnd[1]));
            barrier.setBarrierstart(Integer.parseInt(coordinatesStart[0]));
            barrier.setBarrierend(Integer.parseInt(coordinatesEnd[0]));

            if(barrier.getBarrierend() < barrier.barrierend){
                int temp = barrier.getBarrierend();
                barrier.setBarrierend(barrier.barrierstart);
                barrier.setBarrierend(temp);
            }

        }

    }

    public void connectionParser(String destinationCoordinate){
        String temp = removeFirstandLast(destinationCoordinate);
        String []coordinates = temp.split(",");

        connection.setDestinationCoordinateX(Integer.parseInt(coordinates[0]));
        connection.setDestinationCoordinateY(Integer.parseInt(coordinates[1]));
    }


    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        field = new Field();
        field.setWidth(width);
        field.setHeight(height);
        field.setFieldID(UUID.randomUUID());
        fieldList.add(field);
        return field.getFieldID();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, String barrierString) {

        barrier = new Barrier();
        barrier.setBarrierId(UUID.randomUUID());

        barrierParser(barrierString); //ermittelt koordinaten der barrier

        for (Field value : fieldList) {
            if (value.getFieldID() == fieldId) {
                value.barrierList.add(barrier);
            }
        }

    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {

        connection = new Connection();
        connection.setConnectionID(UUID.randomUUID());
        connection.setSourceField(sourceFieldId);
        connection.setSourceCoordinates(sourceCoordinate);
        connection.setDestinationField(destinationFieldId);
        connection.setDestinationCoordinates(destinationCoordinate);

       connectionParser(destinationCoordinate);  //ermittelt koordinaten der destination

        for (Field value : fieldList) {
            if (value.getFieldID() == sourceFieldId) {
                value.connectionList.add(connection);
            }
        }

        return connection.getConnectionID();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        machine = new MiningMachine();
        machine.setMachineID(UUID.randomUUID());
        machine.setName(name);
        machine.setCoordinateX(0);
        machine.setCoordinateY(0);
        machineList.add(machine);
        return machine.getMachineID();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */

    public Boolean executeCommand(UUID miningMachineId, String commandString) {

        String input = removeFirstandLast(commandString);   //commandString wird zerlegt und vorbereitet für switch
        String[] inputArray = input.split(",");
        String input1 = inputArray[0];
        String input2 = inputArray[1];

       switch (input1) {

           case ("en"):
               for (Field value : fieldList) {
                   if (value.getFieldID().toString().equals(input2)) {

                       for (MiningMachine miningMachine : machineList) {
                           if (miningMachine.getFieldID() == value.getFieldID() && miningMachine.getCoordinateX() == 0 && miningMachine.getCoordinateY() == 0) {  //entrance ist belegt
                               return false;
                           }
                       }

                       for (MiningMachine miningMachine : machineList) {
                           if (miningMachine.getMachineID() == miningMachineId) {
                               miningMachine.setFieldID(value.getFieldID());
                               miningMachine.setCoordinateX(0);
                               miningMachine.setCoordinateY(0);
                           }
                       }
                   }
               }
               break;


           case ("tr"):

               for (Field value : fieldList) {

                   for (int x = 0; x < value.connectionList.size(); x++) {

                       for (MiningMachine miningMachine : machineList) {

                           if (getCoordinates(miningMachine.getMachineID()).equals(value.connectionList.get(x).getSourceCoordinates()) //machine sitzt auf korrekten koordinaten & field aus command existiert
                                   && value.connectionList.get(x).getDestinationField().equals(UUID.fromString(input2))) {

                               miningMachine.setCoordinateX(value.connectionList.get(x).destinationCoordinateX);
                               miningMachine.setCoordinateY(value.connectionList.get(x).destinationCoordinateY);
                               miningMachine.setFieldID(value.connectionList.get(x).getDestinationField());
                               return true;
                           }
                       }
                   }
               }
               return false;

           case ("we"):

               for (MiningMachine miningMachine : machineList) {
                   for (int i = 0; i < Integer.parseInt(input2); i++) {
                       if (miningMachine.getMachineID() == miningMachineId) {
                           int corX = miningMachine.getCoordinateX();
                           miningMachine.setCoordinateX(corX - 1);
                           for (Field value : fieldList) {
                               if (miningMachine.getFieldID() == value.getFieldID()) {
                                   for (int z = 0; z < value.getBarrierList().size(); z++) {
                                       if (miningMachine.getCoordinateX() == value.getBarrierList().get(z).leveX
                                               && miningMachine.getCoordinateY() >= value.getBarrierList().get(z).barrierstart
                                               && miningMachine.getCoordinateY() < value.getBarrierList().get(z).barrierend) {
                                           miningMachine.setCoordinateX(corX + 1);
                                           break;
                                       }
                                   }
                                   if (miningMachine.getCoordinateX() < 0) {
                                       miningMachine.setCoordinateX(miningMachine.getCoordinateX() + 1);
                                       break;
                                   }
                               }
                           }
                       }
                   }

               }
               break;

           case ("so"):

               for (MiningMachine miningMachine : machineList) {
                   for (int i = 0; i < Integer.parseInt(input2); i++) {
                       if (miningMachine.getMachineID() == miningMachineId) {
                           int corY = miningMachine.getCoordinateY();
                           miningMachine.setCoordinateY(corY - 1);

                           for (Field value : fieldList) {
                               if (miningMachine.getFieldID() == value.getFieldID()) {
                                   for (int z = 0; z < value.getBarrierList().size(); z++) {
                                       if (miningMachine.getCoordinateY() == value.getBarrierList().get(z).leveY
                                               && miningMachine.getCoordinateX() >= value.getBarrierList().get(z).barrierstart
                                               && miningMachine.getCoordinateX() < value.getBarrierList().get(z).barrierend) {
                                           break;
                                       }
                                   }
                                   if (miningMachine.getCoordinateY() < 0) {

                                       miningMachine.setCoordinateY(miningMachine.getCoordinateY() + 1);
                                       break;
                                   }
                               }
                           }
                       }
                   }
               }
               break;

           case ("ea"):
               for (int i = 0; i < Integer.parseInt(input2); i++) {

                   for (MiningMachine miningMachine : machineList) {

                       if (miningMachine.getMachineID() == miningMachineId) {

                           int corX = miningMachine.getCoordinateX();
                           miningMachine.setCoordinateX(corX + 1);

                           for (Field value : fieldList) {

                               if (miningMachine.getFieldID() == value.getFieldID()) {

                                   for (int z = 0; z < value.getBarrierList().size(); z++) {

                                       if (miningMachine.getCoordinateX() == value.getBarrierList().get(z).leveX
                                               && miningMachine.getCoordinateY() >= value.getBarrierList().get(z).barrierstart
                                               && miningMachine.getCoordinateY() < value.getBarrierList().get(z).barrierend) {

                                           break;
                                       }
                                   }

                                   if (miningMachine.getCoordinateX() > value.getWidth()) {
                                       miningMachine.setCoordinateX(corX - 1);
                                       break;
                                   }
                               }
                           }
                       }
                   }
               }
               break;

           case ("no"):

               for (MiningMachine miningMachine : machineList) {
                   for (int i = 0; i < Integer.parseInt(input2); i++) {
                       if (miningMachine.getMachineID() == miningMachineId) {

                           int corY = miningMachine.getCoordinateY();
                           miningMachine.setCoordinateY(corY + 1);

                           for (Field value : fieldList) {

                               if (miningMachine.getFieldID() == value.getFieldID()) {

                                   for (int z = 0; z < value.getBarrierList().size(); z++) {

                                       if (miningMachine.getCoordinateY() == value.getBarrierList().get(z).leveY
                                               && miningMachine.getCoordinateX() >= value.getBarrierList().get(z).barrierstart
                                               && miningMachine.getCoordinateX() < value.getBarrierList().get(z).barrierend) {

                                           miningMachine.setCoordinateY(miningMachine.getCoordinateY() - 1);

                                       }
                                   }
                                   if (miningMachine.getCoordinateY() > value.getHeight()) {
                                       miningMachine.setCoordinateY(corY - 1);
                                   }

                               }

                           }
                       }
                   }
               }
               break;
        }
        return true;
       }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        UUID fieldID ;

        for (MiningMachine miningMachine : machineList) {
            if (miningMachine.getMachineID() == miningMachineId) {
                fieldID = miningMachine.fieldID;
                return fieldID;
            }
        }
        return null;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        String coordinates;

        for (MiningMachine miningMachine : machineList) {
            if (miningMachine.getMachineID() == miningMachineId) {
                coordinates = "(" + miningMachine.getCoordinateX() + "," + miningMachine.coordinateY + ")";
                return coordinates;
            }
        }
        return null;
    }


}
